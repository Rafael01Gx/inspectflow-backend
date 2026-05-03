package br.com.inspectflow.domain.notification.repositories;

import br.com.inspectflow.domain.notification.models.NotificationGroup;
import br.com.inspectflow.domain.user.enums.Role;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface NotificationGroupRepository {
    Set<UUID> getMemberIds(UUID groupId);
    void addMember(UUID groupId, UUID userId);
    void addMemberByRole(UUID userId, Role role);
    void removeMember(UUID groupId, UUID userId);
    void removeFromAllGroups(UUID userId);
    Optional<NotificationGroup> findGroupByLinkedRole(Role role);
}
