package br.com.inspectflow.application.auth.ports.out;

import org.springframework.security.core.Authentication;

public interface IdentityProviderPort {
    Authentication authenticate(String email, String password);
    Authentication createAuthentication(String email, String role);
}
