package br.com.inspectflow.infrastructure.security.adapters;

import br.com.inspectflow.application.auth.ports.out.IdentityProviderPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@RequiredArgsConstructor
public class SpringSecurityIdentityProviderAdapter implements IdentityProviderPort {

    private final AuthenticationManager authenticationManager;

    @Override
    public Authentication authenticate(String email, String password) {
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );
    }

    @Override
    public Authentication createAuthentication(String email, String role) {
        return new UsernamePasswordAuthenticationToken(
                email,
                null,
                Collections.singleton(new SimpleGrantedAuthority("ROLE_" + role))
        );
    }
}
