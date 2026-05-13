package br.com.inspectflow.infrastructure.persistence.postgres.dashboard;

import br.com.inspectflow.application.dashboard.dto.CriticalStockDto;
import br.com.inspectflow.application.dashboard.dto.OpenOrderByPriorityDto;
import br.com.inspectflow.application.dashboard.dto.OverdueInspectionDto;
import br.com.inspectflow.application.dashboard.ports.out.PlantHealthQueryRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PlantHealthQueryRepositoryImpl implements PlantHealthQueryRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public long countEquipmentsWithOverdueInspection() {
        String sql = """
                SELECT COUNT(DISTINCT e.id)
                FROM equipments e
                JOIN equipment_health_sheets h ON h.equipment_id = e.id
                WHERE h.next_mechanical_inspection < NOW()
                   OR h.next_electrical_inspection  < NOW()
                   OR h.next_calibration             < NOW()
                """;
        return ((Number) em.createNativeQuery(sql).getSingleResult()).longValue();
    }

    @Override
    public long countEquipmentsWithCriticalOpenOrder() {
        String sql = """
                SELECT COUNT(DISTINCT wo.equipment_id)
                FROM work_orders wo
                WHERE wo.order_status NOT IN ('COMPLETED', 'CANCELLED')
                  AND wo.order_priority = 'CRITICAL'
                  AND wo.equipment_id IS NOT NULL
                """;
        return ((Number) em.createNativeQuery(sql).getSingleResult()).longValue();
    }

    @Override
    public long countStockItemsBelowMinimum() {
        String sql = """
                SELECT COUNT(*)
                FROM stock_items
                WHERE min_quantity IS NOT NULL
                  AND quantity < min_quantity
                """;
        return ((Number) em.createNativeQuery(sql).getSingleResult()).longValue();
    }

    @Override
    public long countTotalEquipments() {
        String sql = "SELECT COUNT(*) FROM equipments";
        return ((Number) em.createNativeQuery(sql).getSingleResult()).longValue();
    }

    @Override
    public Page<OverdueInspectionDto> findOverdueInspections(Pageable pageable) {
        String sql = """
                SELECT
                    e.id::text,
                    e.name,
                    CASE
                        WHEN h.next_mechanical_inspection < NOW() AND h.next_electrical_inspection < NOW() AND h.next_calibration < NOW() THEN 'ALL'
                        WHEN h.next_mechanical_inspection < NOW() AND h.next_electrical_inspection < NOW() THEN 'MECHANICAL_ELECTRICAL'
                        WHEN h.next_mechanical_inspection < NOW() AND h.next_calibration            < NOW() THEN 'MECHANICAL_CALIBRATION'
                        WHEN h.next_electrical_inspection  < NOW() AND h.next_calibration           < NOW() THEN 'ELECTRICAL_CALIBRATION'
                        WHEN h.next_mechanical_inspection < NOW() THEN 'MECHANICAL'
                        WHEN h.next_electrical_inspection  < NOW() THEN 'ELECTRICAL'
                        ELSE 'CALIBRATION'
                    END AS overdue_type,
                    GREATEST(
                        COALESCE(EXTRACT(DAY FROM NOW() - h.next_mechanical_inspection), 0),
                        COALESCE(EXTRACT(DAY FROM NOW() - h.next_electrical_inspection),  0),
                        COALESCE(EXTRACT(DAY FROM NOW() - h.next_calibration),            0)
                    )::bigint AS overdue_days,
                    h.last_mechanical_inspection,
                    h.last_electrical_inspection,
                    h.last_calibration,
                    h.next_mechanical_inspection,
                    h.next_electrical_inspection,
                    h.next_calibration
                FROM equipments e
                JOIN equipment_health_sheets h ON h.equipment_id = e.id
                WHERE h.next_mechanical_inspection < NOW()
                   OR h.next_electrical_inspection  < NOW()
                   OR h.next_calibration             < NOW()
                ORDER BY overdue_days DESC
                LIMIT :limit OFFSET :offset
                """;

        String countSql = """
                SELECT COUNT(*)
                FROM equipments e
                JOIN equipment_health_sheets h ON h.equipment_id = e.id
                WHERE h.next_mechanical_inspection < NOW()
                   OR h.next_electrical_inspection  < NOW()
                   OR h.next_calibration             < NOW()
                """;

        @SuppressWarnings("unchecked")
        List<Object[]> rows = em.createNativeQuery(sql)
                .setParameter("limit", pageable.getPageSize())
                .setParameter("offset", pageable.getOffset())
                .getResultList();

        List<OverdueInspectionDto> content = rows.stream().map(r -> new OverdueInspectionDto(
                (String) r[0],
                (String) r[1],
                (String) r[2],
                ((Number) r[3]).longValue(),
                r[4] != null ? ((java.sql.Timestamp) r[4]).toLocalDateTime() : null,
                r[5] != null ? ((java.sql.Timestamp) r[5]).toLocalDateTime() : null,
                r[6] != null ? ((java.sql.Timestamp) r[6]).toLocalDateTime() : null,
                r[7] != null ? ((java.sql.Timestamp) r[7]).toLocalDateTime() : null,
                r[8] != null ? ((java.sql.Timestamp) r[8]).toLocalDateTime() : null,
                r[9] != null ? ((java.sql.Timestamp) r[9]).toLocalDateTime() : null
        )).toList();

        long total = ((Number) em.createNativeQuery(countSql).getSingleResult()).longValue();
        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public List<OpenOrderByPriorityDto> findOpenOrdersByPriority() {
        String sql = """
                SELECT
                    e.name                           AS equipment_name,
                    wo.order_priority,
                    COUNT(*)                         AS total,
                    MIN(wo.due_date)::text           AS earliest_due
                FROM work_orders wo
                JOIN equipments e ON e.id = wo.equipment_id
                WHERE wo.order_status NOT IN ('COMPLETED', 'CANCELLED')
                GROUP BY e.name, wo.order_priority
                ORDER BY MIN(wo.due_date)
                """;

        @SuppressWarnings("unchecked")
        List<Object[]> rows = em.createNativeQuery(sql).getResultList();

        return rows.stream().map(r -> new OpenOrderByPriorityDto(
                (String) r[0],
                (String) r[1],
                ((Number) r[2]).longValue(),
                (String) r[3]
        )).toList();
    }

    @Override
    public List<CriticalStockDto> findCriticalStockItems() {
        String sql = """
                SELECT
                    id,
                    name,
                    part_category,
                    quantity,
                    min_quantity,
                    (min_quantity - quantity) AS deficit,
                    location
                FROM stock_items
                WHERE min_quantity IS NOT NULL
                  AND quantity < min_quantity
                ORDER BY (min_quantity - quantity) DESC
                """;

        @SuppressWarnings("unchecked")
        List<Object[]> rows = em.createNativeQuery(sql).getResultList();

        return rows.stream().map(r -> new CriticalStockDto(
                ((Number) r[0]).longValue(),
                (String) r[1],
                (String) r[2],
                ((Number) r[3]).intValue(),
                r[4] != null ? ((Number) r[4]).intValue() : null,
                ((Number) r[5]).intValue(),
                (String) r[6]
        )).toList();
    }
}
