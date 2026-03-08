package br.com.inspectflow.application.services.auth;

import jakarta.servlet.http.Cookie;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CookieService {

    @Value("${app.security.jwt.cookie-name}")
    private String cookieName;

    public Cookie createSessionCookie(String token) {
        var cookie = new Cookie(cookieName, token);

        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(3600);

        cookie.setAttribute("SameSite", "Strict");

        return cookie;
    }
}