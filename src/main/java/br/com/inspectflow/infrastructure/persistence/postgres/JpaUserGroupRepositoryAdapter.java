package br.com.inspectflow.infrastructure.persistence.postgres;

import br.com.inspectflow.domain.notification.models.NotificationGroupMember;
import br.com.inspectflow.domain.notification.repositories.UserGroupRepository;
import br.com.inspectflow.infrastructure.persistence.postgres.repositories.PostgresNotificationGroupMemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
public class JpaUserGroupRepositoryAdapter implements UserGroupRepository {

    private final PostgresNotificationGroupMemberRepository repository;


    @Override
    public Set<UUID> getMemberIds(UUID groupId) {
        return repository.findByIdGroupId(groupId)
                .stream()
                .map(NotificationGroupMember::getUserId)
                .collect(Collectors.toSet());
    }
}