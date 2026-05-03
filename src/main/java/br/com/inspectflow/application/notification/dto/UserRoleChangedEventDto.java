package br.com.inspectflow.application.notification.dto;

import br.com.inspectflow.domain.user.enums.Role;

import java.util.UUID;

public record UserRoleChangedEventDto(
        UUID userId,
        Role oldRole,
        Role newRole
) {
}
