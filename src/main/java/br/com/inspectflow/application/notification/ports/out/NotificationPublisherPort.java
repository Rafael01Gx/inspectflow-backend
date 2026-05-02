package br.com.inspectflow.application.notification.ports.out;

import br.com.inspectflow.application.notification.dto.NotificationDto;

import java.util.UUID;

public interface NotificationPublisherPort {
    void publishToUser(UUID userId, NotificationDto notification);
    void publishHeartbeat();
}
