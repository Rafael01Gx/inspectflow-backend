package br.com.inspectflow.infrastructure.persistence.postgres.order;

import br.com.inspectflow.domain.order.enums.OrderAttachmentType;
import br.com.inspectflow.domain.order.models.OrderAttachment;
import br.com.inspectflow.domain.order.repositories.OrderAttachmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JpaOrderAttachmentAdapter implements OrderAttachmentRepository {
    private final PostgresOrderAttachmentRepository repository;

    @Override
    public Optional<OrderAttachment> findById(UUID id) {
        return repository.findById(id);
    }

    @Override
    public boolean existsByWorkOrderIdAndType(UUID workOrderId, OrderAttachmentType type) {
        return repository.existsByOrderIdAndType(workOrderId, type);
    }
}
