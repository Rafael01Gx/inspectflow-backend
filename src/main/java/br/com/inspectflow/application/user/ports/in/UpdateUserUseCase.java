package br.com.inspectflow.application.user.ports.in;

import br.com.inspectflow.application.user.dto.UpdateUserRequest;
import br.com.inspectflow.application.user.dto.UserResponse;
import java.util.UUID;

public interface UpdateUserUseCase {
    UserResponse execute(UUID id, UpdateUserRequest dto);
}
