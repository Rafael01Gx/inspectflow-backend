package br.com.inspectflow.infrastructure.persistence.postgres;

import br.com.inspectflow.domain.notification.models.Notification;
import br.com.inspectflow.domain.notification.repositories.NotificationRepository;
import br.com.inspectflow.infrastructure.persistence.postgres.repositories.PostgresNotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JpaNotificationRepositoryAdapter implements NotificationRepository {

    private final PostgresNotificationRepository repository;


    @Override
    public Notification save(Notification notification) {
        return repository.save(notification);
    }

    @Override
    public Optional<Notification> findById(UUID id) {
        return repository.findById(id);
    }

    @Override
    public List<Notification> findUnreadByRecipient(UUID recipientId) {
        return repository
                .findByRecipientIdAndReadFalseOrderByCreatedAtDesc(recipientId);
    }

    @Override
    public List<Notification> findByRecipient(UUID recipientId) {
        return repository.findByRecipientIdOrderByCreatedAtDesc(recipientId);
    }

    @Override
    public long countUnreadByRecipient(UUID recipientId) {
        return repository.countByRecipientIdAndReadFalse(recipientId);
    }

    @Override
    public void markAllAsReadByRecipient(UUID recipientId) {
        repository.markAllAsReadByRecipientId(recipientId);
    }


}
