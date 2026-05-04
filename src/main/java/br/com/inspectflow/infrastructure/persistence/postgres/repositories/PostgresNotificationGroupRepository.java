package br.com.inspectflow.infrastructure.persistence.postgres.repositories;

import br.com.inspectflow.domain.notification.models.NotificationGroup;
import br.com.inspectflow.domain.user.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface PostgresNotificationGroupRepository extends JpaRepository<NotificationGroup, UUID> {
    Optional<NotificationGroup> findByLinkedRole(Role linlkedRole);
    List<NotificationGroup> findByLinkedRoleIn(Set<Role> linkedRoles);


}