package br.com.inspectflow.application.order.events.listener;

import br.com.inspectflow.application.notification.templates.CompleteOrderNotification;
import br.com.inspectflow.application.order.dto.CompleteWorkOrderNotificationDto;
import br.com.inspectflow.application.order.events.CompleteWorkOrderEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
@Slf4j
public class CompleteWorkOrderEventListener {
    private final CompleteOrderNotification notification;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onCompleteWorkOrder(CompleteWorkOrderEvent event){
        try {
            notification.execute(
                    CompleteWorkOrderNotificationDto.builder()
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
