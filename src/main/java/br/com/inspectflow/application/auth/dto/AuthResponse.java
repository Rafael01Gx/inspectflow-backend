package br.com.inspectflow.application.auth.dto;

import br.com.inspectflow.application.user.dto.UserResponse;

public record AuthResponse(
        String token,
        UserResponse user
) {
}
