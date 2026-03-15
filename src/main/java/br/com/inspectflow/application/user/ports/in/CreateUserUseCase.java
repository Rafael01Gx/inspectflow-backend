package br.com.inspectflow.application.user.ports.in;

import br.com.inspectflow.application.user.dto.CreateUserRequest;
import br.com.inspectflow.application.user.dto.UserResponse;

public interface CreateUserUseCase {
    UserResponse execute(CreateUserRequest dto);
}
