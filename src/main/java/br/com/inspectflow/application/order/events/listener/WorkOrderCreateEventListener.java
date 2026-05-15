package br.com.inspectflow.application.order.events.listener;

import br.com.inspectflow.application.email.dto.SendWorkOrderCreatedMailRequest;
import br.com.inspectflow.application.email.ports.in.SendWorkOrderCreatedMailUseCase;
import br.com.inspectflow.application.notification.templates.CreateOrderNotification;
import br.com.inspectflow.application.order.events.WorkOrderCreatedEvent;
import br.com.inspectflow.domain.notification.enums.NotificationType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
@Slf4j
public class WorkOrderCreateEventListener {

    private final CreateOrderNotification notification;
    private final SendWorkOrderCreatedMailUseCase sendWorkOrderCreatedMailUseCase;


    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onWorkOrderCreated(WorkOrderCreatedEvent event) {
        try {
            notification.execute(event, NotificationType.INFO);
        } catch (Exception ex) {
            log.error("Falha ao enviar notificação para WorkOrder id={}: {}", event.id(), ex.getMessage(), ex);
        }

        try {
            sendWorkOrderCreatedMailUseCase.execute(SendWorkOrderCreatedMailRequest.from(event));
        } catch (Exception ex) {
            log.error("Falha ao enviar email para WorkOrder id={}: {}", event.id(), ex.getMessage(), ex);
        }
    }
}