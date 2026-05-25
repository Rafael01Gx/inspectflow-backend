package br.com.inspectflow.application.order.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record CompleteWorkOrderNotificationDto(
        UUID id,
        String title,
        String equipmentName,
        String orderStatus,
        String assignee,
        String performedWork,
        LocalDateTime completionDate
) {
}
