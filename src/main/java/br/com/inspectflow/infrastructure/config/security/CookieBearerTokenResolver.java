package br.com.inspectflow.infrastructure.config.security;

import br.com.inspectflow.infrastructure.config.properties.JwtProperties;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CookieBearerTokenResolver implements BearerTokenResolver {

    private final JwtProperties properties;

    @Override
    public String resolve(HttpServletRequest request) {

        String path = request.getServletPath();

        if (path.equals("/auth/login") ||
                path.equals("/auth/recovery") ||
                path.equals("/auth/recovery-password") ||
                path.equals("/auth/logout")
        ) {
            return null;
        }

        if (request.getCookies() == null) {
            return null;
        }

        for (Cookie cookie : request.getCookies()) {
            if (properties.cookieName().equals(cookie.getName())) {
                return cookie.getValue();
            }
        }

        return null;
    }
}