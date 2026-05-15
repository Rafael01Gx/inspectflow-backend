package br.com.inspectflow.application.order.events.publisher;

import br.com.inspectflow.application.order.events.WorkOrderDeleteDocumentEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WorkOrderDeleteDocumentPublisher {

    private final ApplicationEventPublisher eventPublisher;

    public void publishDeleteDocument(WorkOrderDeleteDocumentEvent event){
        eventPublisher.publishEvent(event);
    }


}
