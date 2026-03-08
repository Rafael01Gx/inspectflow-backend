package br.com.inspectflow.domain.user.dto.out;

import br.com.inspectflow.domain.user.enums.Role;

import java.util.UUID;

public record UserResponse(
        UUID id,
        String email,
        Role role,
        Boolean active
) {

}
