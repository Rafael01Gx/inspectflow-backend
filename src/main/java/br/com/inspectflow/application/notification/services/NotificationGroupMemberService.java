package br.com.inspectflow.application.notification.services;

import br.com.inspectflow.application.notification.dto.UserRoleChangedEventDto;
import br.com.inspectflow.domain.notification.repositories.NotificationGroupRepository;
import br.com.inspectflow.domain.user.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificationGroupMemberService {

    private final NotificationGroupRepository groupRepository;

    @EventListener
    @Transactional
    public void onUserRoleChanged(UserRoleChangedEventDto event) {
        groupRepository.removeFromAllGroups(event.userId());
        groupRepository.findGroupByLinkedRole(event.newRole())
                .ifPresent(group -> groupRepository.addMember(group.getId(), event.userId()));
    }

    @Transactional
    public void addMember(UUID groupId, UUID userId) {
        groupRepository.addMember(groupId, userId);
    }

    @Transactional
    public void addMemberByRole(UUID userId, Role role) {
        groupRepository.addMemberByRole(userId, role);
    }

    @Transactional
    public void removeMember(UUID groupId, UUID userId) {
        groupRepository.removeMember(groupId, userId);
    }

}