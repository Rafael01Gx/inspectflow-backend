package br.com.inspectflow.application.auth.ports.in;

import br.com.inspectflow.application.auth.dto.RegisterRequest;

public interface RegisterUseCase {
    void execute(RegisterRequest request);
}
