package br.com.inspectflow.application.auth;

import br.com.inspectflow.infrastructure.config.properties.JwtProperties;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CookieService {

    private final JwtProperties properties;

    public Cookie createSessionCookie(String token) {
        var cookie = new Cookie(properties.cookieName(), token);

        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(3600);

        cookie.setAttribute("SameSite", "Strict");

        return cookie;
    }

    public Cookie clearSessionCookie() {

        Cookie cookie = new Cookie(properties.cookieName(), "");

        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);

        return cookie;
    }
}