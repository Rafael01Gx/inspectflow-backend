package br.com.inspectflow.infrastructure.persistence.postgres.notification;

import br.com.inspectflow.domain.notification.models.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface PostgresNotificationRepository extends JpaRepository<Notification,UUID> {

    List<Notification> findByRecipientIdAndReadFalseOrderByCreatedAtDesc(UUID recipientId);

    List<Notification> findByRecipientIdOrderByCreatedAtDesc(UUID recipientId);

    long countByRecipientIdAndReadFalse(UUID recipientId);

    @Modifying
    @Query("UPDATE Notification n SET n.read = true, n.readAt = CURRENT_TIMESTAMP " +
            "WHERE n.recipientId = :recipientId AND n.read = false")
    void markAllAsReadByRecipientId(@Param("recipientId") UUID recipientId);

    void deleteByExpiresAtBefore(Instant instant);
    @Modifying
    @Query("DELETE FROM Notification n WHERE n.read = true AND n.readAt < :threshold")
    void deleteReadBefore(@Param("threshold") Instant threshold);
}
