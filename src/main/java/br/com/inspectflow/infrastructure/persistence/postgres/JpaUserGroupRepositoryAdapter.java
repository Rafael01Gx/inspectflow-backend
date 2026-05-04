package br.com.inspectflow.infrastructure.persistence.postgres;

import br.com.inspectflow.application.http.handlers.BusinessException;
import br.com.inspectflow.domain.notification.models.NotificationGroup;
import br.com.inspectflow.domain.notification.models.NotificationGroupMember;
import br.com.inspectflow.domain.notification.repositories.UserGroupRepository;
import br.com.inspectflow.domain.user.enums.Role;
import br.com.inspectflow.infrastructure.persistence.postgres.repositories.PostgresNotificationGroupMemberRepository;
import br.com.inspectflow.infrastructure.persistence.postgres.repositories.PostgresNotificationGroupRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
public class JpaUserGroupRepositoryAdapter implements UserGroupRepository {

    private final PostgresNotificationGroupMemberRepository repository;
    private final PostgresNotificationGroupRepository groupRepository;


    @Override
    public Set<UUID> getMemberIds(Role role) {
        var group = groupRepository.findByLinkedRole(role).orElseThrow(()->new BusinessException("Grupo não encontrado"));
        return repository.findByIdGroupId(group.getId())
                .stream()
                .map(NotificationGroupMember::getUserId)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<NotificationGroupMember> getMemberIdsWithRoles(Set<Role> roles) {
        if (roles == null || roles.isEmpty()) {
            return Set.of();
        }
        var groupId = groupRepository.findByLinkedRoleIn(roles).stream().map(NotificationGroup::getId).collect(Collectors.toSet());

        return new HashSet<>(repository.findByIdGroupIdIn(groupId));

    }
}