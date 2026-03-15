package br.com.inspectflow.application.auth.ports.in;

import br.com.inspectflow.application.auth.dto.AuthResponse;

public interface AuthenticateUseCase {
    AuthResponse execute(String email, String password);
}
