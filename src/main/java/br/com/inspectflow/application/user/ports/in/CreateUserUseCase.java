package br.com.inspectflow.application.user.ports.in;

import br.com.inspectflow.adapters.in.web.user.dto.CreateUserRequest;
import br.com.inspectflow.adapters.in.web.user.dto.UserResponse;

public interface CreateUserUseCase {
    UserResponse execute(CreateUserRequest request);
}
