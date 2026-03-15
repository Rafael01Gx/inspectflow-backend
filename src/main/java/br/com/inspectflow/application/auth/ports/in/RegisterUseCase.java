package br.com.inspectflow.application.auth.ports.in;

import br.com.inspectflow.application.auth.dto.AuthResponse;
import br.com.inspectflow.application.auth.dto.RegisterRequest;

public interface RegisterUseCase {
    AuthResponse execute(RegisterRequest request);
}
