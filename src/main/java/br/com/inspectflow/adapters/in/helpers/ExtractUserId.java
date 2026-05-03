package br.com.inspectflow.adapters.in.helpers;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.UUID;

public class ExtractUserId {
    public static UUID fromAuthentication(Authentication authentication) {
        if (authentication instanceof JwtAuthenticationToken jwtAuth) {
            String userId = jwtAuth.getToken().getClaimAsString("userId");
            return UUID.fromString(userId);
        }
        throw new IllegalStateException("Tipo de autenticação não suportado: "
                + authentication.getClass());
    }
}
