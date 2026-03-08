package br.com.inspectflow.domain.auth.dto.out;

import br.com.inspectflow.domain.user.dto.out.UserResponse;

public record AuthResult(
        String token,
        UserResponse user
) {
}
