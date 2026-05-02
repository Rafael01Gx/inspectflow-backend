package br.com.inspectflow.application.notification.dto;

import br.com.inspectflow.domain.notification.models.NotificationGroup;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

public record NotificationGroupDto(
        UUID id,
        String name,
        String description,
        Set<UUID> memberIds,
        Instant createdAt
) {

    public static NotificationGroupDto from(NotificationGroup entity, Set<UUID> memberIds){
        return new NotificationGroupDto(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                memberIds,
                entity.getCreatedAt()
        );
    }
}
