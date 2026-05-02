package br.com.inspectflow.infrastructure.persistence.postgres;

import br.com.inspectflow.application.notification.dto.CreateGroupDto;
import br.com.inspectflow.application.notification.dto.NotificationGroupDto;
import br.com.inspectflow.domain.notification.models.NotificationGroup;
import br.com.inspectflow.domain.notification.models.NotificationGroupMember;
import br.com.inspectflow.domain.notification.repositories.NotificationGroupRepository;
import br.com.inspectflow.infrastructure.persistence.postgres.repositories.PostgresNotificationGroupMemberRepository;
import br.com.inspectflow.infrastructure.persistence.postgres.repositories.PostgresNotificationGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JpaNotificationGroupRepositoryAdapter implements NotificationGroupRepository {

    private final PostgresNotificationGroupRepository groupRepository;
    private final PostgresNotificationGroupMemberRepository memberRepository;

    @Override
    public NotificationGroupDto save(CreateGroupDto dto) {
        NotificationGroup entity = new NotificationGroup();
        entity.setName(dto.name());
        entity.setDescription(dto.description());
        NotificationGroup saved = groupRepository.save(entity);


        if (dto.memberIds() != null) {
            dto.memberIds().forEach(userId -> addMember(saved.getId(), userId));
        }

        return NotificationGroupDto.from(saved, getMemberIds(saved.getId()));
    }

    @Override
    public Optional<NotificationGroupDto> findById(UUID groupId) {
        return groupRepository.findById(groupId)
                .map(e -> NotificationGroupDto.from(e, getMemberIds(e.getId())));
    }

    @Override
    public List<NotificationGroupDto> findAll() {
        List<NotificationGroup> groups = groupRepository.findAll();

        if (groups.isEmpty()) return List.of();

        Set<UUID> groupIds = groups.stream()
                .map(NotificationGroup::getId)
                .collect(Collectors.toSet());

        Map<UUID, Set<UUID>> membersByGroup = memberRepository
                .findByIdGroupIdIn(groupIds)
                .stream()
                .collect(Collectors.groupingBy(
                        NotificationGroupMember::getGroupId,
                        Collectors.mapping(
                                NotificationGroupMember::getUserId,
                                Collectors.toSet()
                        )
                ));

        return groups.stream()
                .map(e -> NotificationGroupDto.from(e, membersByGroup.getOrDefault(e.getId(), Set.of())))
                .toList();
    }

    @Override
    public void addMember(UUID groupId, UUID userId) {
        NotificationGroupMember member = new NotificationGroupMember();
        member.setId(new NotificationGroupMember
                .NotificationGroupMemberId(groupId, userId));
        memberRepository.save(member);
    }

    @Override
    @Transactional
    public void removeMember(UUID groupId, UUID userId) {
        memberRepository.deleteByIdGroupIdAndIdUserId(groupId, userId);
    }

    @Override
    public Set<UUID> getMemberIds(UUID groupId) {
        return memberRepository.findByIdGroupId(groupId)
                .stream()
                .map(NotificationGroupMember::getUserId)
                .collect(Collectors.toSet());
    }

    @Override
    public boolean exists(UUID groupId) {
        return groupRepository.existsById(groupId);
    }


}