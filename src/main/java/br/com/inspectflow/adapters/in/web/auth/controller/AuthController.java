package br.com.inspectflow.adapters.in.web.auth.controller;

import br.com.inspectflow.application.auth.dto.AuthResponse;
import br.com.inspectflow.application.auth.dto.LoginRequest;
import br.com.inspectflow.application.auth.ports.in.AuthenticateUseCase;
import br.com.inspectflow.application.auth.ports.in.ClearSessionCookieUseCase;
import br.com.inspectflow.application.auth.ports.in.CreateSessionCookieUseCase;
import br.com.inspectflow.application.user.dto.EmailRequest;
import br.com.inspectflow.application.user.dto.ResetPasswordRequest;
import br.com.inspectflow.application.user.dto.UserResponse;
import br.com.inspectflow.application.user.ports.in.FindUserByEmailUseCase;
import br.com.inspectflow.application.user.ports.in.RecoveryPasswordUseCase;
import br.com.inspectflow.application.user.ports.in.ResetUserPasswordUseCase;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth/")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticateUseCase authenticate;
    private final CreateSessionCookieUseCase createSessionCookie;
    private final ClearSessionCookieUseCase clearSessionCookie;
    private final FindUserByEmailUseCase findUserByEmailService;
    private final RecoveryPasswordUseCase recoveryPassword;
    private final ResetUserPasswordUseCase resetUserPassword;


    @PostMapping("/login")
    public ResponseEntity<@NonNull UserResponse> login(
            @Valid @RequestBody LoginRequest request,
            HttpServletResponse response) {

        AuthResponse authResult = authenticate.execute(
                request.email(),
                request.password()
        );

        Cookie cookie = createSessionCookie.execute(authResult.token());
        response.addCookie(cookie);
        return ResponseEntity.ok(authResult.user());
    }


    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {

        Cookie cookie = clearSessionCookie.execute();
        response.addCookie(cookie);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/recovery-password")
    public ResponseEntity<Void> recoveryPassword(@RequestParam String token,@Valid @RequestBody ResetPasswordRequest dto) {
        resetUserPassword.execute(token,dto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/recovery")
    public ResponseEntity<Void> recoveryByEmail( @RequestBody @Valid EmailRequest dto) {
        recoveryPassword.execute(dto.email());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> me(Authentication authentication) {
        if (authentication.getName() == null) {
            return ResponseEntity.ok(null);
        }
        return ResponseEntity.ok(findUserByEmailService.execute(authentication.getName()));
    }
}
