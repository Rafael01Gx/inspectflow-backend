package br.com.inspectflow.application.notification.services;

import br.com.inspectflow.application.notification.dto.NotificationDto;
import br.com.inspectflow.application.notification.dto.SendNotificationDto;
import br.com.inspectflow.application.notification.ports.in.QueryNotificationUseCase;
import br.com.inspectflow.application.notification.ports.in.SendNotificationUseCase;
import br.com.inspectflow.application.notification.ports.out.NotificationPublisherPort;
import br.com.inspectflow.domain.notification.repositories.UserGroupRepository;
import br.com.inspectflow.domain.notification.models.Notification;
import br.com.inspectflow.domain.notification.repositories.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificationService implements SendNotificationUseCase, QueryNotificationUseCase {

    private final NotificationRepository repository;
    private final NotificationPublisherPort publisher;
    private final UserGroupRepository groupRepository;

    @Override
    @Transactional
    @Async("notificationExecutor")
    public void sendToUser(SendNotificationDto dto) {
        Notification notification =  Notification.builder()
                .recipientId(dto.recipientId())
                .type(dto.type())
                .title(dto.title())
                .message(dto.message())
                .metadata(dto.metadata())
                .expiresAt(dto.expiresAt())
                .build();

        Notification saved = repository.save(notification);
        publisher.publishToUser(dto.recipientId(), NotificationDto.from(saved));
    }

    @Override
    @Async("notificationExecutor")
    public void sendToGroup(UUID groupId, SendNotificationDto dto) {
        Set<UUID> memberIds = groupRepository.getMemberIds(groupId);

        memberIds.forEach(userId -> {
            Notification notification = Notification.builder()
                    .recipientId(userId)
                    .groupId(groupId)
                    .type(dto.type())
                    .title(dto.title())
                    .message(dto.message())
                    .metadata(dto.metadata())
                    .expiresAt(dto.expiresAt())
                    .build();

            Notification saved = repository.save(notification);
            publisher.publishToUser(userId, NotificationDto.from(saved));
        });
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotificationDto> getUnread(UUID userId) {
        return repository.findUnreadByRecipient(userId)
                .stream()
                .map(NotificationDto::from)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotificationDto> getAll(UUID userId) {
        return repository.findByRecipient(userId)
                .stream()
                .map(NotificationDto::from)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public long countUnread(UUID userId) {
        return repository.countUnreadByRecipient(userId);
    }

    @Override
    public void markAsRead(UUID notificationId, UUID userId) {
        repository.findById(notificationId)
                .filter(n -> n.belongsTo(userId))
                .ifPresent(n -> {
                    n.markAsRead();
                    repository.save(n);
                });
    }

    @Override
    public void markAllAsRead(UUID userId) {
        repository.markAllAsReadByRecipient(userId);
    }
}
