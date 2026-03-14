package br.com.inspectflow.adapters.in.web.auth.controller;

import br.com.inspectflow.adapters.in.web.auth.dto.AuthResponse;
import br.com.inspectflow.adapters.in.web.auth.dto.LoginRequest;
import br.com.inspectflow.adapters.in.web.auth.dto.RegisterRequest;
import br.com.inspectflow.adapters.in.web.user.dto.UserResponse;
import br.com.inspectflow.application.auth.CookieService;
import br.com.inspectflow.application.auth.ports.in.AuthenticateUseCase;
import br.com.inspectflow.application.auth.ports.in.RegisterUseCase;
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
    private final AuthenticateUseCase authenticate;
    private final RegisterUseCase register;
    private final CookieService cookieService;

    @PostMapping("/login")
    public ResponseEntity<@NonNull UserResponse> login(
            @Valid @RequestBody LoginRequest request,
            HttpServletResponse response) {

        AuthResponse authResult = authenticate.execute(
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

        AuthResponse result = register.execute(request);

        Cookie cookie = cookieService.createSessionCookie(result.token());

        response.addCookie(cookie);

        return ResponseEntity.ok(result.user());
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {

        Cookie cookie = cookieService.clearSessionCookie();
        response.addCookie(cookie);

        return ResponseEntity.noContent().build();
    }
}
