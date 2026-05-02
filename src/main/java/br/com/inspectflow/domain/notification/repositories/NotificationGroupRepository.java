package br.com.inspectflow.domain.notification.repositories;

import br.com.inspectflow.application.notification.dto.CreateGroupDto;
import br.com.inspectflow.application.notification.dto.NotificationGroupDto;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface NotificationGroupRepository {
    NotificationGroupDto save(CreateGroupDto command);
    Optional<NotificationGroupDto> findById(UUID groupId);
    List<NotificationGroupDto> findAll();
    void addMember(UUID groupId, UUID userId);
    void removeMember(UUID groupId, UUID userId);
    Set<UUID> getMemberIds(UUID groupId);
    boolean exists(UUID groupId);
}
