package br.com.inspectflow.adapters.in.controllers;

import br.com.inspectflow.application.services.auth.AuthService;
import br.com.inspectflow.application.services.auth.CookieService;
import br.com.inspectflow.domain.auth.dto.in.LoginRequest;
import br.com.inspectflow.domain.auth.dto.in.RegisterRequest;
import br.com.inspectflow.domain.auth.dto.out.AuthResult;
import br.com.inspectflow.domain.user.dto.out.UserResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth/")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final CookieService cookieService;

    @PostMapping("/login")
    public ResponseEntity<@NonNull UserResponse> login(
            @Valid @RequestBody LoginRequest request,
            HttpServletResponse response) {

        AuthResult authResult = authService.authenticate(
                request.email(),
                request.password()
        );

        Cookie cookie = cookieService.createSessionCookie(authResult.token());
        response.addCookie(cookie);
        return ResponseEntity.ok(authResult.user());
    }

    @PostMapping("/register")
    public ResponseEntity<@NonNull UserResponse> register(
            @Valid @RequestBody RegisterRequest request,
            HttpServletResponse response
    ) {

        AuthResult result = authService.register(request);

        Cookie cookie = cookieService.createSessionCookie(result.token());

        response.addCookie(cookie);

        return ResponseEntity.ok(result.user());
    }
}
