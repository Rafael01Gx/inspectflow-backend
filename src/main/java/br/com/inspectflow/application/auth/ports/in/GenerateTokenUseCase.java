package br.com.inspectflow.application.auth.ports.in;

import org.springframework.security.core.Authentication;

public interface GenerateTokenUseCase {
    String execute(Authentication authentication);
}
