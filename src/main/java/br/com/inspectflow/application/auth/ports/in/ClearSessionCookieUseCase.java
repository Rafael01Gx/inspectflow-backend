package br.com.inspectflow.application.auth.ports.in;

import jakarta.servlet.http.Cookie;

public interface ClearSessionCookieUseCase {
    Cookie execute();
}
