package br.com.inspectflow.infrastructure.scheduling;

import br.com.inspectflow.domain.user.repositories.PasswordResetTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
@RequiredArgsConstructor
public class TokenPurgeTask {

    private final PasswordResetTokenRepository tokenRepository;

    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional
    public void purgeExpiredTokens() {
        log.info("Iniciando limpeza de tokens de recuperação expirados...");

        long now = System.currentTimeMillis();
        tokenRepository.deleteAllExpiredSince(now);

        log.info("Limpeza de tokens concluída.");
    }
}
