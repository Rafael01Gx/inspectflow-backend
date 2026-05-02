package br.com.inspectflow.application.notification.ports.in;

import br.com.inspectflow.application.notification.dto.SendNotificationDto;

import java.util.UUID;

public interface SendNotificationUseCase {
    void sendToUser(SendNotificationDto command);
    void sendToGroup(UUID groupId, SendNotificationDto command);
    void broadcast(SendNotificationDto command);
}