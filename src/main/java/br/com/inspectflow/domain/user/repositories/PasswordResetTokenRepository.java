package br.com.inspectflow.domain.user.repositories;

import br.com.inspectflow.domain.user.models.PasswordResetToken;

import java.util.Optional;

public interface PasswordResetTokenRepository {

    PasswordResetToken save(PasswordResetToken resetToken);

    void delete(PasswordResetToken token);

    Optional<PasswordResetToken> findByToken(String token);
    void deleteAllExpiredSince(long now);
}
