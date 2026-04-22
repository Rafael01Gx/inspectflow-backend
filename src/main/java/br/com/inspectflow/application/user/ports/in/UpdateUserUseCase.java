package br.com.inspectflow.application.user.ports.in;

import br.com.inspectflow.application.user.dto.UpdateUserRequest;
import br.com.inspectflow.application.user.dto.UserResponse;
import org.springframework.security.core.Authentication;

import java.util.UUID;

public interface UpdateUserUseCase {
    UserResponse execute(UUID id, Authentication user, UpdateUserRequest dto);
}
