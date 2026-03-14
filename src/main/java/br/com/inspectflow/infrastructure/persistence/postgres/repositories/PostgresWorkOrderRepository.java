package br.com.inspectflow.infrastructure.persistence.postgres.repositories;

import br.com.inspectflow.domain.order.models.WorkOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface PostgresWorkOrderRepository extends JpaRepository<WorkOrder, UUID> {
}
