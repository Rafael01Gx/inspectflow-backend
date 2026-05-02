package br.com.inspectflow.application.notification.dto;

import br.com.inspectflow.domain.notification.enums.NotificationType;
import br.com.inspectflow.domain.notification.models.Notification;

import java.time.Instant;
import java.util.UUID;

public record NotificationDto(
        UUID id,
        UUID recipientId,
        UUID groupId,
        NotificationType type,
        String title,
        String message,
        String metadata,
        boolean read,
        Instant readAt,
        Instant createdAt
) {
    public static NotificationDto from(Notification n) {
        return new NotificationDto(
                n.getId(),
                n.getRecipientId(),
                n.getGroupId(),
                n.getType(),
                n.getTitle(),
                n.getMessage(),
                n.getMetadata(),
                n.isRead(),
                n.getReadAt(),
                n.getCreatedAt()
        );
    }
}