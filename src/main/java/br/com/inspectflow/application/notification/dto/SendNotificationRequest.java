package br.com.inspectflow.application.notification.dto;

import br.com.inspectflow.domain.notification.enums.NotificationType;

import java.time.Instant;
import java.util.UUID;

public record SendNotificationRequest(
        UUID recipientId,
        NotificationType type,
        String title,
        String message,
        String metadata,
        Instant expiresAt
) {
}
