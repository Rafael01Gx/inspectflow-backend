package br.com.inspectflow.domain.notification.repositories;

import br.com.inspectflow.domain.notification.models.NotificationGroupMember;

import java.util.List;
import java.util.UUID;

public interface NotificationGroupMemberRepository {

    List<NotificationGroupMember> findByIdGroupId(UUID groupId);
    void deleteByIdUserId(UUID userId);
    void deleteByIdGroupIdAndIdUserId(UUID groupId, UUID userId);

    void save(NotificationGroupMember member);

}
