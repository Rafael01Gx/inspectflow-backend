package br.com.inspectflow.application.user.services;

import br.com.inspectflow.application.email.ports.in.SendRecoveryMailUseCase;
import br.com.inspectflow.application.user.ports.in.RecoveryPasswordUseCase;
import br.com.inspectflow.domain.user.models.PasswordResetToken;
import br.com.inspectflow.domain.user.models.User;
import br.com.inspectflow.domain.user.repositories.PasswordResetTokenRepository;
import br.com.inspectflow.domain.user.repositories.UserRepository;
import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RecoveryPasswordService implements RecoveryPasswordUseCase {
    private final UserRepository userRepository;
    private final PasswordResetTokenRepository tokenRepository;
    private final SendRecoveryMailUseCase recoveryEmailService;


    @Override
    @Transactional
    @Observed(name = "user.recovery",
            contextualName = "solicita recuperação de credenciais")
    public void execute(String email) {
        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            return;
        }

        String token = UUID.randomUUID().toString();

        PasswordResetToken resetToken = new PasswordResetToken(token, user);
        tokenRepository.save(resetToken);

        recoveryEmailService.execute(user.getEmail(), user.getName(), token);
    }
}
