package br.com.inspectflow.adapters.in.web.auth.dto;

import br.com.inspectflow.adapters.in.web.user.dto.UserResponse;

public record AuthResponse(
        String token,
        UserResponse user
) {
}
