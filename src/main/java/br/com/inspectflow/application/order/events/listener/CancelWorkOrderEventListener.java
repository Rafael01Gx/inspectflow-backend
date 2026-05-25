package br.com.inspectflow.application.order.events.listener;

import br.com.inspectflow.application.notification.templates.CancelOrderNotification;
import br.com.inspectflow.application.order.dto.CancelWorkOrderNotificationDto;
import br.com.inspectflow.application.order.events.CancelWorkOrderEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
@Slf4j
public class CancelWorkOrderEventListener {
    private final CancelOrderNotification notification;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onCancelWorkOrder(CancelWorkOrderEvent event) {

        try {
            notification.execute(
                    CancelWorkOrderNotificationDto.builder()
                            .id(event.id())
                            .title(event.title())
                            .equipmentName(event.equipmentName())
                            .orderStatus(event.orderStatus())
                            .assignee(event.assignee())
                            .performedWork(event.performedWork())
                            .completionDate(event.completionDate())
                            .build());
        } catch (Exception e) {
            log.error("Falha ao enviar notificação para WorkOrder id={}: {}", event.id(), e.getMessage(), e);
        }

    }
}
