package br.com.inspectflow.application.user.ports.in;

import br.com.inspectflow.application.user.dto.UserResponse;

public interface FindUserByEmailUseCase {
    UserResponse execute(String email);
}
