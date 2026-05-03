package br.com.inspectflow.infrastructure.persistence.postgres;

import br.com.inspectflow.domain.notification.models.NotificationGroup;
import br.com.inspectflow.domain.notification.models.NotificationGroupMember;
import br.com.inspectflow.domain.notification.repositories.NotificationGroupRepository;
import br.com.inspectflow.domain.user.enums.Role;
import br.com.inspectflow.infrastructure.persistence.postgres.repositories.PostgresNotificationGroupMemberRepository;
import br.com.inspectflow.infrastructure.persistence.postgres.repositories.PostgresNotificationGroupRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JpaNotificationGroupRepositoryAdapter implements NotificationGroupRepository {

    private final PostgresNotificationGroupRepository groupRepository;
    private final PostgresNotificationGroupMemberRepository memberRepository;

    @Override
    public Set<UUID> getMemberIds(UUID groupId) {
        return memberRepository.findByIdGroupId(groupId)
                .stream()
                .map(NotificationGroupMember::getUserId)
                .collect(Collectors.toSet());
    }

    @Override
    public void addMember(UUID groupId, UUID userId) {
        NotificationGroupMember member = new NotificationGroupMember();
        member.setId(new NotificationGroupMember
                .NotificationGroupMemberId(groupId, userId));
        memberRepository.save(member);
    }

    @Override
    public void addMemberByRole(UUID userId,Role role) {
        var groupId = groupRepository.findByLinkedRole(role).orElseThrow(EntityNotFoundException::new);
        NotificationGroupMember member = new NotificationGroupMember();
        member.setId(new NotificationGroupMember
                .NotificationGroupMemberId(groupId.getId(), userId));
        memberRepository.save(member);
    }


    @Override
    @Transactional
    public void removeMember(UUID groupId, UUID userId) {
        memberRepository.deleteByIdGroupIdAndIdUserId(groupId, userId);
    }

    @Override
    @Transactional
    public void removeFromAllGroups(UUID userId) {
        memberRepository.deleteByIdUserId(userId);
    }

    @Override
    public Optional<NotificationGroup> findGroupByLinkedRole(Role role) {
        return groupRepository.findByLinkedRole(role);
    }

}