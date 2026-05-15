package br.com.inspectflow.application.order.events.publisher;

import br.com.inspectflow.application.order.events.WorkOrderRollBackMinio;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WorkOrderRollBackEventPublisher {

    private final ApplicationEventPublisher eventPublisher;


    public void publishRollBackMinio(WorkOrderRollBackMinio event) {
        eventPublisher.publishEvent(event);
    }
}
