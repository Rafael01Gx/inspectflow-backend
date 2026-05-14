package br.com.inspectflow.infrastructure.persistence.postgres.dashboard;

import br.com.inspectflow.application.dashboard.dto.EquipmentResolutionDto;
import br.com.inspectflow.application.dashboard.dto.FailureTrendDto;
import br.com.inspectflow.application.dashboard.dto.TopEquipmentByOrdersDto;
import br.com.inspectflow.application.dashboard.dto.TopPartUsedDto;
import br.com.inspectflow.application.dashboard.ports.out.EquipmentAnalyticsQueryRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EquipmentAnalyticsQueryRepositoryImpl implements EquipmentAnalyticsQueryRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<TopEquipmentByOrdersDto> findTopEquipmentsByOrders(int limit) {
        String sql = """
                SELECT
                    e.id::text,
                    e.name,
                    COUNT(wo.id)                                                                   AS total_orders,
                    COUNT(wo.id) FILTER (WHERE wo.order_status = 'COMPLETED')                      AS completed_orders,
                    COUNT(wo.id) FILTER (WHERE wo.order_status NOT IN ('COMPLETED','CANCELLED'))    AS in_progress_orders,
                    AVG(
                        EXTRACT(EPOCH FROM (wo.completion_date::timestamp - wo.created_at)) / 3600.0
                    ) FILTER (WHERE wo.completion_date IS NOT NULL)                                AS avg_resolution_hours
                FROM work_orders wo
                JOIN equipments e ON e.id = wo.equipment_id
                GROUP BY e.id, e.name
                ORDER BY total_orders DESC
                LIMIT :limit
                """;

        @SuppressWarnings("unchecked")
        List<Object[]> rows = em.createNativeQuery(sql)
                .setParameter("limit", limit)
                .getResultList();

        return rows.stream().map(r -> new TopEquipmentByOrdersDto(
                (String) r[0],
                (String) r[1],
                ((Number) r[2]).longValue(),
                ((Number) r[3]).longValue(),
                ((Number) r[4]).longValue(),
                r[5] != null ? ((Number) r[5]).doubleValue() : null
        )).toList();
    }

    @Override
    public List<TopPartUsedDto> findTopPartsUsed(int limit) {
        String sql = """
                SELECT
                    si.id,
                    si.name,
                    si.part_category,
                    SUM(siu.quantity_used)            AS total_used,
                    COUNT(DISTINCT siu.work_order_id) AS used_in_orders,
                    si.quantity                       AS current_stock,
                    si.min_quantity
                FROM stock_item_usages siu
                JOIN stock_items si ON si.id = siu.stock_item_id
                GROUP BY si.id, si.name, si.part_category, si.quantity, si.min_quantity
                ORDER BY total_used DESC
                LIMIT :limit
                """;

        @SuppressWarnings("unchecked")
        List<Object[]> rows = em.createNativeQuery(sql)
                .setParameter("limit", limit)
                .getResultList();

        return rows.stream().map(r -> new TopPartUsedDto(
                ((Number) r[0]).longValue(),
                (String) r[1],
                (String) r[2],
                ((Number) r[3]).longValue(),
                ((Number) r[4]).longValue(),
                ((Number) r[5]).intValue(),
                r[6] != null ? ((Number) r[6]).intValue() : null
        )).toList();
    }

    @Override
    public List<FailureTrendDto> findFailureTrend(int months) {
        String sql = """
                SELECT
                    TO_CHAR(wo.created_at, 'YYYY-MM') AS month,
                    e.name                             AS equipment_name,
                    wo.order_priority,
                    COUNT(*)                           AS total
                FROM work_orders wo
                JOIN equipments e ON e.id = wo.equipment_id
                WHERE wo.created_at >= NOW() - CAST(:months || ' months' AS INTERVAL)
                GROUP BY month, e.name, wo.order_priority
                ORDER BY month, e.name
                """;

        @SuppressWarnings("unchecked")
        List<Object[]> rows = em.createNativeQuery(sql)
                .setParameter("months", months)
                .getResultList();

        return rows.stream().map(r -> new FailureTrendDto(
                (String) r[0],
                (String) r[1],
                (String) r[2],
                ((Number) r[3]).longValue()
        )).toList();
    }

    @Override
    public List<EquipmentResolutionDto> findEquipmentResolutionRanking(int limit) {
        String sql = """
                SELECT
                    e.id::text,
                    e.name,
                    AVG(
                        EXTRACT(EPOCH FROM (wo.completion_date::timestamp - wo.created_at)) / 3600.0
                    )                                                                              AS avg_resolution_hours,
                    COUNT(wo.id)                                                                   AS total_completed,
                    MIN(
                        EXTRACT(EPOCH FROM (wo.completion_date::timestamp - wo.created_at)) / 3600.0
                    )                                                                              AS min_resolution_hours,
                    MAX(
                        EXTRACT(EPOCH FROM (wo.completion_date::timestamp - wo.created_at)) / 3600.0
                    )                                                                              AS max_resolution_hours
                FROM work_orders wo
                JOIN equipments e ON e.id = wo.equipment_id
                WHERE wo.order_status = 'COMPLETED'
                  AND wo.completion_date IS NOT NULL
                GROUP BY e.id, e.name
                ORDER BY avg_resolution_hours ASC
                LIMIT :limit
                """;

        @SuppressWarnings("unchecked")
        List<Object[]> rows = em.createNativeQuery(sql)
                .setParameter("limit", limit)
                .getResultList();

        return rows.stream().map(r -> new EquipmentResolutionDto(
                (String) r[0],
                (String) r[1],
                r[2] != null ? ((Number) r[2]).doubleValue() : null,
                ((Number) r[3]).longValue(),
                r[4] != null ? ((Number) r[4]).doubleValue() : null,
                r[5] != null ? ((Number) r[5]).doubleValue() : null
        )).toList();
    }
}