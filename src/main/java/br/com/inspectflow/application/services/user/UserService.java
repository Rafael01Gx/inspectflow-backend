package br.com.inspectflow.application.services.user;

import br.com.inspectflow.application.usecases.UserUseCase;
import br.com.inspectflow.domain.auth.dto.in.LoginRequest;
import br.com.inspectflow.domain.user.dto.out.UserResponse;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserUseCase {

    @Override
    public UserResponse create(LoginRequest loginRequest) {
        return null;
    }
}
