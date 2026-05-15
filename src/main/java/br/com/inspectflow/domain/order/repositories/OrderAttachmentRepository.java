package br.com.inspectflow.domain.order.repositories;

import br.com.inspectflow.domain.order.enums.OrderAttachmentType;
import br.com.inspectflow.domain.order.models.OrderAttachment;

import java.util.Optional;
import java.util.UUID;

public interface OrderAttachmentRepository {

    Optional<OrderAttachment> findById(UUID id);
    boolean existsByWorkOrderIdAndType(UUID workOrderId, OrderAttachmentType type);
}
