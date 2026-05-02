package br.com.inspectflow.application.notification.services;

import br.com.inspectflow.application.notification.dto.CreateGroupDto;
import br.com.inspectflow.application.notification.dto.NotificationGroupDto;
import br.com.inspectflow.application.notification.ports.in.NotificationGroupUseCase;
import br.com.inspectflow.domain.notification.repositories.NotificationGroupRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class NotificationGroupService implements NotificationGroupUseCase {

    private final NotificationGroupRepository groupRepository;

    @Override
    public NotificationGroupDto create(CreateGroupDto dto) {
        return groupRepository.save(dto);
    }

    @Override
    public NotificationGroupDto addMember(UUID groupId, UUID userId) {
        if (!groupRepository.exists(groupId)) {
            throw new EntityNotFoundException("Grupo não encontrado: " + groupId);
        }
        groupRepository.addMember(groupId, userId);
        return groupRepository.findById(groupId).orElseThrow();
    }

    @Override
    public NotificationGroupDto removeMember(UUID groupId, UUID userId) {
        groupRepository.removeMember(groupId, userId);
        return groupRepository.findById(groupId).orElseThrow();
    }

    @Override
    public NotificationGroupDto findById(UUID groupId) {
        return groupRepository.findById(groupId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Grupo não encontrado: " + groupId));
    }

    @Override
    public List<NotificationGroupDto> findAll() {
        return groupRepository.findAll();
    }
}