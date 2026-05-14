package br.com.inspectflow.application.user.events.publisher;

import br.com.inspectflow.application.user.events.CreateUserEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CreateUserEventPublisher {
    private final ApplicationEventPublisher eventPublisher;

    public void publishCreated(CreateUserEvent user) {
        eventPublisher.publishEvent(user);
    }
}
