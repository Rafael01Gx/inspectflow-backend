package br.com.inspectflow.infrastructure.persistence.postgres.dashboard;

import br.com.inspectflow.application.dashboard.dto.PersonalActivityDto;
import br.com.inspectflow.application.dashboard.dto.PersonalSummaryDto;
import br.com.inspectflow.application.dashboard.dto.PersonalWorkOrderSummaryDto;
import br.com.inspectflow.application.dashboard.dto.PersonalWorkOrderTimelineDto;
import br.com.inspectflow.application.dashboard.ports.out.PersonalDashboardQueryRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class PersonalDashboardQueryRepositoryImpl implements PersonalDashboardQueryRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public PersonalSummaryDto findPersonalSummary(UUID userId) {
        String workOrderSql = """
                SELECT
                    COUNT(*) FILTER (WHERE order_status NOT IN ('COMPLETED','CANCELLED'))           AS open_orders,
                    COUNT(*) FILTER (WHERE order_status = 'COMPLETED'
                                      AND completion_date = CURRENT_DATE)                           AS completed_today,
                    COUNT(*) FILTER (WHERE order_status = 'COMPLETED'
                                      AND completion_date >= DATE_TRUNC('month', CURRENT_DATE))     AS completed_month,
                    COUNT(*) FILTER (WHERE order_status NOT IN ('COMPLETED','CANCELLED')
                                      AND due_date < CURRENT_DATE)                                  AS overdue_orders
                FROM work_orders
                WHERE assignee_id = :userId
                """;

        String inspectionMonthSql = """
                SELECT COUNT(*)
                FROM inspection_history
                WHERE inspector_id = :userId
                  AND date >= DATE_TRUNC('month', NOW())
                """;

        String inspectionWeekSql = """
                SELECT COUNT(*)
                FROM inspection_history
                WHERE inspector_id = :userId
                  AND date >= DATE_TRUNC('week', NOW())
                """;

        String complianceSql = """
                SELECT
                    COUNT(*) FILTER (WHERE status = 'APPROVED' AND date <= NOW())  AS on_time,
                    COUNT(*) FILTER (WHERE date <= NOW())                           AS total
                FROM inspection_history
                WHERE inspector_id = :userId
                """;

        Object[] wo = (Object[]) em.createNativeQuery(workOrderSql)
                .setParameter("userId", userId)
                .getSingleResult();

        long inspMonth = ((Number) em.createNativeQuery(inspectionMonthSql)
                .setParameter("userId", userId)
                .getSingleResult()).longValue();

        long inspWeek = ((Number) em.createNativeQuery(inspectionWeekSql)
                .setParameter("userId", userId)
                .getSingleResult()).longValue();

        Object[] comp = (Object[]) em.createNativeQuery(complianceSql)
                .setParameter("userId", userId)
                .getSingleResult();

        long onTime = ((Number) comp[0]).longValue();
        long total  = ((Number) comp[1]).longValue();
        double complianceRate = total > 0 ? (double) onTime / total * 100.0 : 0.0;

        return new PersonalSummaryDto(
                ((Number) wo[0]).longValue(),
                ((Number) wo[1]).longValue(),
                ((Number) wo[2]).longValue(),
                ((Number) wo[3]).longValue(),
                inspMonth,
                inspWeek,
                complianceRate
        );
    }

    @Override
    public List<PersonalActivityDto> findActivityByPeriod(UUID userId, String groupBy) {
        String truncUnit = resolveGroupBy(groupBy);
        String sql = """
                SELECT
                    TO_CHAR(DATE_TRUNC('%s', ih.date), 'YYYY-MM-DD') AS period,
                    COUNT(*)                                           AS inspections_done,
                    COUNT(*) FILTER (WHERE ih.status = 'APPROVED')    AS approved,
                    COUNT(*) FILTER (WHERE ih.status = 'REJECTED')    AS rejected,
                    COUNT(*) FILTER (WHERE ih.status = 'PENDING')     AS pending
                FROM inspection_history ih
                WHERE ih.inspector_id = :userId
                  AND ih.date >= NOW() - INTERVAL '90 days'
                GROUP BY DATE_TRUNC('%s', ih.date)
                ORDER BY DATE_TRUNC('%s', ih.date)
                """.formatted(truncUnit, truncUnit, truncUnit);

        @SuppressWarnings("unchecked")
        List<Object[]> rows = em.createNativeQuery(sql)
                .setParameter("userId", userId)
                .getResultList();

        return rows.stream().map(r -> new PersonalActivityDto(
                (String) r[0],
                ((Number) r[1]).longValue(),
                ((Number) r[2]).longValue(),
                ((Number) r[3]).longValue(),
                ((Number) r[4]).longValue()
        )).toList();
    }

    @Override
    public List<PersonalWorkOrderTimelineDto> findWorkOrderTimeline(UUID userId, int months) {
        String sql = """
                SELECT
                    TO_CHAR(wo.created_at, 'YYYY-MM-DD')                           AS day,
                    COUNT(*) FILTER (WHERE order_status = 'COMPLETED')              AS completed,
                    COUNT(*) FILTER (WHERE order_status = 'IN_PROGRESS')            AS in_progress,
                    COUNT(*) FILTER (WHERE order_status = 'PENDING')                AS pending
                FROM work_orders wo
                WHERE wo.assignee_id = :userId
                  AND wo.created_at >= NOW() - CAST(:months || ' months' AS INTERVAL)
                GROUP BY TO_CHAR(wo.created_at, 'YYYY-MM-DD')
                ORDER BY day
                """;

        @SuppressWarnings("unchecked")
        List<Object[]> rows = em.createNativeQuery(sql)
                .setParameter("userId", userId)
                .setParameter("months", months)
                .getResultList();

        return rows.stream().map(r -> new PersonalWorkOrderTimelineDto(
                (String) r[0],
                ((Number) r[1]).longValue(),
                ((Number) r[2]).longValue(),
                ((Number) r[3]).longValue()
        )).toList();
    }

    @Override
    public List<PersonalWorkOrderSummaryDto> findPendingWorkOrders(UUID userId) {
        String sql = """
                SELECT
                    wo.id::text,
                    wo.title,
                    wo.equipment_name,
                    wo.order_status,
                    wo.order_priority,
                    wo.due_date::text,
                    wo.completion_date::text
                FROM work_orders wo
                WHERE wo.assignee_id = :userId
                  AND wo.order_status NOT IN ('COMPLETED', 'CANCELLED')
                ORDER BY wo.due_date ASC
                """;

        @SuppressWarnings("unchecked")
        List<Object[]> rows = em.createNativeQuery(sql)
                .setParameter("userId", userId)
                .getResultList();

        return rows.stream().map(r -> new PersonalWorkOrderSummaryDto(
                (String) r[0],
                (String) r[1],
                (String) r[2],
                (String) r[3],
                (String) r[4],
                (String) r[5],
                (String) r[6]
        )).toList();
    }

    @Override
    public List<PersonalWorkOrderSummaryDto> findRecentCompletedWorkOrders(UUID userId, int limit) {
        String sql = """
                SELECT
                    wo.id::text,
                    wo.title,
                    wo.equipment_name,
                    wo.order_status,
                    wo.order_priority,
                    wo.due_date::text,
                    wo.completion_date::text
                FROM work_orders wo
                WHERE wo.assignee_id = :userId
                  AND wo.order_status = 'COMPLETED'
                ORDER BY wo.completion_date DESC
                LIMIT :limit
                """;

        @SuppressWarnings("unchecked")
        List<Object[]> rows = em.createNativeQuery(sql)
                .setParameter("userId", userId)
                .setParameter("limit", limit)
                .getResultList();

        return rows.stream().map(r -> new PersonalWorkOrderSummaryDto(
                (String) r[0],
                (String) r[1],
                (String) r[2],
                (String) r[3],
                (String) r[4],
                (String) r[5],
                (String) r[6]
        )).toList();
    }

    private String resolveGroupBy(String groupBy) {
        return switch (groupBy.toLowerCase()) {
            case "week"  -> "week";
            case "month" -> "month";
            default      -> "day";
        };
    }
}
