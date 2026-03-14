package br.com.inspectflow.application.user.ports.in;

import br.com.inspectflow.adapters.in.web.user.dto.UpdateUserRequest;
import br.com.inspectflow.adapters.in.web.user.dto.UserResponse;
import java.util.UUID;

public interface UpdateUserUseCase {
    UserResponse execute(UUID id, UpdateUserRequest request);
}
