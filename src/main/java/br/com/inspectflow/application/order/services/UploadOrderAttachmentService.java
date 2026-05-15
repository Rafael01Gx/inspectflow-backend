package br.com.inspectflow.application.order.services;

import br.com.inspectflow.application.bucket.services.UploadFileService;
import br.com.inspectflow.application.http.handlers.BusinessException;
import br.com.inspectflow.application.http.handlers.UserNotFoundException;
import br.com.inspectflow.application.http.handlers.WorkerOrderNotFoundException;
import br.com.inspectflow.application.order.dto.OrderAttachmentRequest;
import br.com.inspectflow.application.order.dto.OrderDetailResponse;
import br.com.inspectflow.application.order.events.WorkOrderAddDocumentEvent;
import br.com.inspectflow.application.order.events.WorkOrderRollBackMinio;
import br.com.inspectflow.application.order.events.publisher.WorkOrderAddDocumentPublisher;
import br.com.inspectflow.application.order.events.publisher.WorkOrderRollBackEventPublisher;
import br.com.inspectflow.application.order.ports.in.UploadOrderAttachmentUseCase;
import br.com.inspectflow.application.order.validators.OrderAttachmentFileIsValid;
import br.com.inspectflow.application.order.validators.WorkOrderUpdatePermissionValidator;
import br.com.inspectflow.domain.bucket.dto.UploadRequest;
import br.com.inspectflow.domain.order.enums.OrderStatus;
import br.com.inspectflow.domain.order.models.OrderAttachment;
import br.com.inspectflow.domain.order.models.WorkOrder;
import br.com.inspectflow.domain.order.repositories.WorkOrderRepository;
import br.com.inspectflow.domain.user.models.User;
import br.com.inspectflow.domain.user.repositories.UserRepository;
import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UploadOrderAttachmentService implements UploadOrderAttachmentUseCase {
    private final OrderAttachmentFileIsValid fileValidator;
    private final UploadFileService uploadFileService;
    private final WorkOrderRepository repository;
    private final UserRepository userRepository;
    private final WorkOrderRollBackEventPublisher publisher;
    private final WorkOrderAddDocumentPublisher docPublisher;
    private final WorkOrderUpdatePermissionValidator hasPermissionValidator;


    @Override
    @Transactional
    @Observed(name = "order.attachment-upload",
            contextualName = "Adiciona documentos a uma ordem de serviço")
    public OrderDetailResponse execute(UUID orderId, UUID userId, OrderAttachmentRequest dto) {

        fileValidator.execute(dto);

        WorkOrder order = repository.findById(orderId).orElseThrow(WorkerOrderNotFoundException::new);

        if (order.getOrderStatus().equals(OrderStatus.CANCELLED) || order.getOrderStatus().equals(OrderStatus.COMPLETED)){
            throw new BusinessException("Uma ordem de serviço completa ou cancelada, não pode ser alterada.");
        }

        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        hasPermissionValidator.execute(order,user);

        UploadRequest uploadResponse = uploadFileService.execute(order.getEquipment().getCode(), dto.type(), dto.file(), orderId);

        OrderAttachment doc = OrderAttachment.builder()
                .type(dto.type())
                .fileName(uploadResponse.fileName())
                .fileUrl(uploadResponse.fileUrl())
                .contentType(dto.file().getContentType())
                .build();
        order.addDocument(doc);

        publisher.publishRollBackMinio(new WorkOrderRollBackMinio(uploadResponse.fileUrl()));
        docPublisher.publishWorkOrderAddDocument(WorkOrderAddDocumentEvent.from(order,doc));
        repository.save(order);



        return OrderDetailResponse.from(order);

    }


}
