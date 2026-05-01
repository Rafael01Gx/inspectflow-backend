package br.com.inspectflow.application.auth.services;

import br.com.inspectflow.application.auth.ports.in.ClearSessionCookieUseCase;
import br.com.inspectflow.application.auth.ports.in.CreateSessionCookieUseCase;
import br.com.inspectflow.infrastructure.config.properties.JwtProperties;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SessionCookieService implements CreateSessionCookieUseCase, ClearSessionCookieUseCase {

    private final JwtProperties properties;

    @Override
    public Cookie execute(String token) {
        var cookie = new Cookie(properties.cookieName(), token);

        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(3600);

        cookie.setAttribute("SameSite", "Strict");

        return cookie;
    }

    @Override
    public Cookie execute() {

        Cookie cookie = new Cookie(properties.cookieName(), "");

        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);

        return cookie;
    }
}