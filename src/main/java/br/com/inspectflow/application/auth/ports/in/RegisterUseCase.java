package br.com.inspectflow.application.auth.ports.in;

import br.com.inspectflow.adapters.in.web.auth.dto.AuthResponse;
import br.com.inspectflow.adapters.in.web.auth.dto.RegisterRequest;

public interface RegisterUseCase {
    AuthResponse execute(RegisterRequest request);
}
