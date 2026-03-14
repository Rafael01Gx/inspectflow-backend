package br.com.inspectflow.application.auth.services;

import br.com.inspectflow.adapters.in.web.auth.dto.AuthResponse;
import br.com.inspectflow.adapters.in.web.auth.dto.RegisterRequest;
import br.com.inspectflow.adapters.in.web.user.dto.CreateUserRequest;
import br.com.inspectflow.adapters.in.web.user.dto.UserResponse;
import br.com.inspectflow.application.auth.TokenService;
import br.com.inspectflow.application.auth.ports.in.AuthenticateUseCase;
import br.com.inspectflow.application.auth.ports.in.RegisterUseCase;
import br.com.inspectflow.application.auth.ports.out.IdentityProviderPort;
import br.com.inspectflow.application.user.ports.in.CreateUserUseCase;
import br.com.inspectflow.application.user.ports.in.FindUserByEmailUseCase;
import br.com.inspectflow.domain.user.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService implements AuthenticateUseCase, RegisterUseCase {

    private final IdentityProviderPort identityProvider;
    private final TokenService tokenService;
    private final CreateUserUseCase createUserUseCase;
    private final FindUserByEmailUseCase findUserByEmailUseCase;


    public AuthResponse authenticate(String email, String password) {
        Authentication authentication = identityProvider.authenticate(email, password);
        String token = tokenService.generateToken(authentication);

        UserResponse userResponse = findUserByEmailUseCase.execute(email);
        return new AuthResponse(token, userResponse);
    }

    @Override
    @Transactional
    public AuthResponse execute(RegisterRequest request) {
        UserResponse userResponse = createUserUseCase.execute(
                new CreateUserRequest(
                        request.name(),
                        request.email(),
                        request.password(),
                        Role.valueOf(request.role())
                )
        );

        Authentication authentication = identityProvider.createAuthentication(userResponse.email(), userResponse.role().toString());
        String token = tokenService.generateToken(authentication);

        return new AuthResponse(token, userResponse);
    }

    @Override
    public AuthResponse execute(String email, String password) {
        return authenticate(email, password);
    }
}
