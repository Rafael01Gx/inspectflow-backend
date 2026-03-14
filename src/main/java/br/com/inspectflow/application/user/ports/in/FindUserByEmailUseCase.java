package br.com.inspectflow.application.user.ports.in;

import br.com.inspectflow.adapters.in.web.user.dto.UserResponse;

public interface FindUserByEmailUseCase {
    UserResponse execute(String email);
}
