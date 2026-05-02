package br.com.inspectflow.infrastructure.persistence.postgres.repositories;

import br.com.inspectflow.domain.notification.models.NotificationGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PostgresNotificationGroupRepository extends JpaRepository<NotificationGroup, UUID> {
}