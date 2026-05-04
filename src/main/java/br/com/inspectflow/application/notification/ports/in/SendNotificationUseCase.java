package br.com.inspectflow.application.notification.ports.in;

import br.com.inspectflow.application.notification.dto.SendNotificationDto;
import br.com.inspectflow.domain.user.enums.Role;

import java.util.Set;

public interface SendNotificationUseCase {
    void sendToUser(SendNotificationDto command);
    void sendToGroup(Set<Role> roles, SendNotificationDto command);
}