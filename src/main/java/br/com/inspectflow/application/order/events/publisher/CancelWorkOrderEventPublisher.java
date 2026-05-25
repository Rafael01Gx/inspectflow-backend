package br.com.inspectflow.application.order.events.publisher;

import br.com.inspectflow.application.order.events.CancelWorkOrderEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CancelWorkOrderEventPublisher {
    private final ApplicationEventPublisher eventPublisher;

    public void publisherCancelWorkOrder(CancelWorkOrderEvent event){eventPublisher.publishEvent(event);}
}
