package br.com.inspectflow.application.order.services;

import br.com.inspectflow.application.http.handlers.AttachmentException;
import br.com.inspectflow.application.http.handlers.WorkerOrderNotFoundException;
import br.com.inspectflow.application.order.events.WorkOrderDeleteDocumentEvent;
import br.com.inspectflow.application.order.events.publisher.WorkOrderDeleteDocumentPublisher;
import br.com.inspectflow.application.order.ports.in.DeleteOrderAttachmentUseCase;
import br.com.inspectflow.domain.order.models.OrderAttachment;
import br.com.inspectflow.domain.order.models.WorkOrder;
import br.com.inspectflow.domain.order.repositories.WorkOrderRepository;
import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeleteOrderAttachmentService implements DeleteOrderAttachmentUseCase {

    private final WorkOrderRepository repository;
    private final WorkOrderDeleteDocumentPublisher publisher;

    @Override
    @Transactional
    @Observed(name = "order.remove-attachment",
            contextualName = "Remove documento de uma ordem de serviço")
    public Void execute(UUID id, UUID documentId) {

        WorkOrder order = repository.findById(id).orElseThrow(WorkerOrderNotFoundException::new);

        OrderAttachment doc = order.getDocuments().stream().filter(f -> f.getId().equals(documentId)).findFirst().orElseThrow(() -> new AttachmentException("Documento não encontrado"));
        order.removeDocument(doc);
        publisher.publishDeleteDocument(new WorkOrderDeleteDocumentEvent(doc.getFileUrl()));

        repository.save(order);

        return null;
    }
}
