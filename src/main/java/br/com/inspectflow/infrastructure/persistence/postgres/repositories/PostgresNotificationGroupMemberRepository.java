package br.com.inspectflow.infrastructure.persistence.postgres.repositories;

import br.com.inspectflow.domain.notification.models.NotificationGroupMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface PostgresNotificationGroupMemberRepository extends JpaRepository<NotificationGroupMember, NotificationGroupMember.NotificationGroupMemberId> {

    List<NotificationGroupMember> findByIdGroupId(UUID groupId);
    List<NotificationGroupMember> findByIdGroupIdIn(Set<UUID> groupIds);
    void deleteByIdGroupIdAndIdUserId(UUID groupId, UUID userId);
    void deleteByIdUserId(UUID userId);

}