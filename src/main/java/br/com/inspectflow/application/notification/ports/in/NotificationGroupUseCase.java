package br.com.inspectflow.application.notification.ports.in;

import br.com.inspectflow.application.notification.dto.CreateGroupDto;
import br.com.inspectflow.application.notification.dto.NotificationGroupDto;

import java.util.List;
import java.util.UUID;

public interface NotificationGroupUseCase {
    NotificationGroupDto create(CreateGroupDto command);
    NotificationGroupDto addMember(UUID groupId, UUID userId);
    NotificationGroupDto removeMember(UUID groupId, UUID userId);
    NotificationGroupDto findById(UUID groupId);
    List<NotificationGroupDto> findAll();
}
