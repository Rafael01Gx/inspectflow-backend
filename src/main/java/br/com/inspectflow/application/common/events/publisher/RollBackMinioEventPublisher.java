package br.com.inspectflow.application.common.events.publisher;

import br.com.inspectflow.application.common.events.RollBackMinio;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RollBackMinioEventPublisher {

    private final ApplicationEventPublisher eventPublisher;


    public void publishRollBackMinio(RollBackMinio event) {
        eventPublisher.publishEvent(event);
    }
}
