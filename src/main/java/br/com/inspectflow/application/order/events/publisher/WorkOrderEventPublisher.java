package br.com.inspectflow.application.order.events.publisher;

import br.com.inspectflow.application.order.events.WorkOrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WorkOrderEventPublisher {

    private final ApplicationEventPublisher eventPublisher;

    public void publishCreated(WorkOrderCreatedEvent order) {
        eventPublisher.publishEvent(order);
    }
}