package br.com.inspectflow.application.user.services;

import br.com.inspectflow.application.http.handlers.InvalidTokenException;
import br.com.inspectflow.application.http.handlers.TokenExpiredException;
import br.com.inspectflow.application.user.dto.ResetPasswordRequest;
import br.com.inspectflow.application.user.ports.in.ResetUserPasswordUseCase;
import br.com.inspectflow.domain.user.models.PasswordResetToken;
import br.com.inspectflow.domain.user.models.User;
import br.com.inspectflow.domain.user.repositories.PasswordResetTokenRepository;
import br.com.inspectflow.domain.user.repositories.UserRepository;
import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ResetUserPasswordService implements ResetUserPasswordUseCase {
    private final PasswordResetTokenRepository tokenRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    @Transactional
    @Observed(name = "user.reset-password",
            contextualName = "recupera credenciais de usuário")
    public void execute(String token, ResetPasswordRequest dto) {
        PasswordResetToken resetToken = tokenRepository.findByToken(token).orElseThrow(InvalidTokenException::new);

        if (resetToken.isExpired()) {
            tokenRepository.delete(resetToken);
            throw new TokenExpiredException();
        }

        User user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(dto.password()));
        userRepository.save(user);

        tokenRepository.delete(resetToken);
    }
}
