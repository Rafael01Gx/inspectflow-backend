package br.com.inspectflow.domain.order.repository;

import br.com.inspectflow.domain.order.models.WorkOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WorkOrderRepository extends JpaRepository<WorkOrder, UUID> {
}
