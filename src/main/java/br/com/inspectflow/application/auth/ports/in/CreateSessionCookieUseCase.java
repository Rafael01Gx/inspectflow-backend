package br.com.inspectflow.application.auth.ports.in;

import jakarta.servlet.http.Cookie;

public interface CreateSessionCookieUseCase {

    Cookie execute(String token);

}
