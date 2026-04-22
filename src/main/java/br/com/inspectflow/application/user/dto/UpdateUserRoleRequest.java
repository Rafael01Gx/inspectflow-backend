package br.com.inspectflow.application.user.dto;

import br.com.inspectflow.domain.user.enums.Role;
import jakarta.validation.constraints.NotNull;

public record UpdateUserRoleRequest(
        @NotNull Role role
) {
}
