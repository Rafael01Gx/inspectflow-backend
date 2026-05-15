package br.com.inspectflow.infrastructure.persistence.postgres.order;

import br.com.inspectflow.domain.order.enums.OrderAttachmentType;
import br.com.inspectflow.domain.order.models.OrderAttachment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PostgresOrderAttachmentRepository extends JpaRepository<OrderAttachment, UUID> {

    boolean existsByOrderIdAndType(UUID orderId, OrderAttachmentType type);
}
