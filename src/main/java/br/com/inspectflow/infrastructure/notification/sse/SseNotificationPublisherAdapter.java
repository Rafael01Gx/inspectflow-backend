package br.com.inspectflow.infrastructure.notification.sse;

import br.com.inspectflow.application.notification.dto.NotificationDto;
import br.com.inspectflow.application.notification.ports.out.NotificationPublisherPort;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class SseNotificationPublisherAdapter implements NotificationPublisherPort {

    private final SseEmitterRegistry registry;

    public SseNotificationPublisherAdapter(SseEmitterRegistry registry) {
        this.registry = registry;
    }

    @Override
    public void publishToUser(UUID userId, NotificationDto notification) {
        registry.sendToUser(userId, notification);
    }

    @Override
    public void publishHeartbeat() {
        registry.sendHeartbeat();
    }
}