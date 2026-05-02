package br.com.inspectflow.application.notification.dto;

import java.util.Set;
import java.util.UUID;

public record CreateGroupDto(
        String name,
        String description,
        Set<UUID> memberIds
) {
}
