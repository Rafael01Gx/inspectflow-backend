package br.com.inspectflow.infrastructure.persistence.postgres.order;

import br.com.inspectflow.domain.order.models.WorkOrder;
import br.com.inspectflow.domain.user.models.User;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface PostgresWorkOrderRepository extends JpaRepository<WorkOrder, UUID>, JpaSpecificationExecutor<WorkOrder> {
    
    @Query("SELECT wo.orderStatus, COUNT(wo) FROM WorkOrder wo GROUP BY wo.orderStatus")
    List<Object[]> countWorkOrdersByStatus();

    @Query("""
    SELECT wo
    FROM WorkOrder wo
    JOIN wo.equipment e
    WHERE e.code = :code
""")
    List<WorkOrder> findAllByEquipmentCode(@Param("code") UUID code);

    @Query(value = """
           SELECT EXTRACT(YEAR FROM wo.created_at) as year,
                  EXTRACT(MONTH FROM wo.created_at) as month,
                  wo.order_status,
                  COUNT(*)
           FROM work_orders wo
           GROUP BY year, month, wo.order_status
           ORDER BY year DESC, month DESC
           """, nativeQuery = true)
    List<Object[]> countWorkOrdersByStatusMonthly();

    // 2. Corrigido para Native Query e cálculo de intervalo Postgres
    @Query(value = """
            SELECT AVG(EXTRACT(EPOCH FROM (wo.completion_date - wo.created_at)) / 3600.0)
            FROM work_orders wo
            WHERE wo.order_status = 'COMPLETED'
            AND wo.completion_date IS NOT NULL
            """, nativeQuery = true)
    Double calculateAverageRepairTimeInHours();

    Page<WorkOrder> findAllByAssignee(User assignee, Pageable pageable);
}
