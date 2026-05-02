package br.com.inspectflow.application.order.services;

import br.com.inspectflow.application.http.handlers.BusinessException;
import br.com.inspectflow.application.http.handlers.UserNotFoundException;
import br.com.inspectflow.application.http.handlers.WorkerOrderNotFoundException;
import br.com.inspectflow.application.notification.dto.SendNotificationDto;
import br.com.inspectflow.application.notification.services.NotificationService;
import br.com.inspectflow.application.order.ports.in.SetAssigneeWorkOrderUseCase;
import br.com.inspectflow.domain.notification.enums.NotificationType;
import br.com.inspectflow.domain.order.enums.OrderStatus;
import br.com.inspectflow.domain.order.models.WorkOrder;
import br.com.inspectflow.domain.order.repositories.WorkOrderRepository;
import br.com.inspectflow.domain.user.models.User;
import br.com.inspectflow.domain.user.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SetAssigneeWorkOrderService implements SetAssigneeWorkOrderUseCase {

    private final WorkOrderRepository repository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;

    @Override
    @Transactional
    public void execute(UUID id, UUID assigneeId) {
        User assignee = userRepository.findById(assigneeId).orElseThrow(UserNotFoundException::new);
        WorkOrder workOrder = repository.findById(id).orElseThrow(WorkerOrderNotFoundException::new);

        if (!workOrder.getOrderStatus().equals(OrderStatus.PENDING)) {
            throw new BusinessException("A ordem de serviço não possui status valído para alteração de responsável!");
        }

        workOrder.setAssignee(assignee);


        notificationService.sendToUser(SendNotificationDto.builder()
                        .recipientId(assigneeId)
                        .title("Selecionado como responsavel")
                        .type(NotificationType.INFO)
                        .message("")
                        .metadata("Local para html metadata")
                        .expiresAt(Instant.now().plusSeconds(30))
                        .build());


        repository.save(workOrder);
    }
}
