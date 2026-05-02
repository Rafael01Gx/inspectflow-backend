package br.com.inspectflow.domain.notification.repositories;

import br.com.inspectflow.domain.notification.models.Notification;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface NotificationRepository {


    Notification save(Notification notification);

    Optional<Notification> findById(UUID id);
    List<Notification> findUnreadByRecipient(UUID recipientId);
    List<Notification> findByRecipient(UUID recipientId, int page, int size);
    long countUnreadByRecipient(UUID recipientId);
    void markAllAsReadByRecipient(UUID recipientId);

}