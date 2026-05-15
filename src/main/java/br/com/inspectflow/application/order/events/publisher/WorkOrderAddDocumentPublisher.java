package br.com.inspectflow.application.order.events.publisher;

import br.com.inspectflow.application.order.events.WorkOrderAddDocumentEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WorkOrderAddDocumentPublisher {
    private final ApplicationEventPublisher eventPublisher;


    public void publishWorkOrderAddDocument(WorkOrderAddDocumentEvent event){
        eventPublisher.publishEvent(event);
    }
}
