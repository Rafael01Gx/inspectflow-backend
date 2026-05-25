package br.com.inspectflow.application.order.events.publisher;

import br.com.inspectflow.application.order.events.CompleteWorkOrderEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CompleteWorkOrderEventPublisher {
    private final ApplicationEventPublisher publisher;

    public void publisherCompleteWorkOrder(CompleteWorkOrderEvent event) {

        publisher.publishEvent(event);

    }
}
