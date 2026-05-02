package br.com.inspectflow.application.notification.dto;

import br.com.inspectflow.domain.notification.enums.NotificationType;
import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

@Builder
public record SendNotificationDto(
        UUID recipientId,
        NotificationType type,
        String title,
        String message,
        String metadata,
        Instant expiresAt
) {
}
