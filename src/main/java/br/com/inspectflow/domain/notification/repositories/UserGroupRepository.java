package br.com.inspectflow.domain.notification.repositories;

import br.com.inspectflow.domain.notification.models.NotificationGroupMember;
import br.com.inspectflow.domain.user.enums.Role;

import java.util.Set;
import java.util.UUID;

public interface UserGroupRepository {
    Set<UUID> getMemberIds(Role role);
    Set<NotificationGroupMember> getMemberIdsWithRoles(Set<Role> roles);
}
