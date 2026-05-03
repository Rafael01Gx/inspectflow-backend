package br.com.inspectflow.application.notification.ports.in;

import br.com.inspectflow.application.notification.dto.NotificationDto;

import java.util.List;
import java.util.UUID;

public interface QueryNotificationUseCase {
    List<NotificationDto> getUnread(UUID userId);
    List<NotificationDto> getAll(UUID userId);
    long countUnread(UUID userId);
    void markAsRead(UUID notificationId, UUID userId);
    void markAllAsRead(UUID userId);
}