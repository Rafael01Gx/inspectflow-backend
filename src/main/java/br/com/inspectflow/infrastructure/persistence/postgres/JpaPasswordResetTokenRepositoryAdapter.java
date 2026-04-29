package br.com.inspectflow.infrastructure.persistence.postgres;

import br.com.inspectflow.domain.user.models.PasswordResetToken;
import br.com.inspectflow.domain.user.repositories.PasswordResetTokenRepository;
import br.com.inspectflow.infrastructure.persistence.postgres.repositories.PostgresPasswordResetTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JpaPasswordResetTokenRepositoryAdapter implements PasswordResetTokenRepository {

    private final PostgresPasswordResetTokenRepository repository;

    @Override
    public PasswordResetToken save(PasswordResetToken resetToken) {
        return repository.save(resetToken);
    }

    @Override
    public void delete(PasswordResetToken token) {
        repository.delete(token);
    }

    @Override
    public Optional<PasswordResetToken> findByToken(String token) {
        return repository.findByToken(token);
    }

    @Override
    public void deleteAllExpiredSince(long now) {
        repository.deleteAllExpiredSince(now);
    }
}
