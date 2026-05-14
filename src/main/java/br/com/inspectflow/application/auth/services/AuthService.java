package br.com.inspectflow.application.auth.services;

import br.com.inspectflow.application.auth.dto.AuthResponse;
import br.com.inspectflow.application.auth.ports.in.AuthenticateUseCase;
import br.com.inspectflow.application.auth.ports.in.GenerateTokenUseCase;
import br.com.inspectflow.application.auth.ports.out.IdentityProviderPort;
import br.com.inspectflow.application.user.dto.UserResponse;
import br.com.inspectflow.application.user.ports.in.CreateUserUseCase;
import br.com.inspectflow.application.user.ports.in.FindUserByEmailUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService implements AuthenticateUseCase {

    private final IdentityProviderPort identityProvider;
    private final GenerateTokenUseCase generateTokenService;
    private final CreateUserUseCase createUserUseCase;
    private final FindUserByEmailUseCase findUserByEmailUseCase;


    public AuthResponse authenticate(String email, String password) {
        Authentication authentication = identityProvider.authenticate(email, password);
        String token = generateTokenService.execute(authentication);

        UserResponse userResponse = findUserByEmailUseCase.execute(email);
        return new AuthResponse(token, userResponse);
    }


    @Override
    public AuthResponse execute(String email, String password) {
        return authenticate(email, password);
    }
}
