package br.com.inspectflow.application.usecases;

import br.com.inspectflow.domain.auth.dto.in.LoginRequest;
import br.com.inspectflow.domain.user.dto.out.UserResponse;

public interface UserUseCase {
    UserResponse create(LoginRequest loginRequest);
}
