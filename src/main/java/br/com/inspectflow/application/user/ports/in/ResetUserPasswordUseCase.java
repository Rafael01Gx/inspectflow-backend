package br.com.inspectflow.application.user.ports.in;

import br.com.inspectflow.application.user.dto.ResetPasswordRequest;

public interface ResetUserPasswordUseCase {
    void execute(String token, ResetPasswordRequest dto);
}
