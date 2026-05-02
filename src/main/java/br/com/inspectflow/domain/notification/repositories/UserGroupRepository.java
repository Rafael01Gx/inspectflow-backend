package br.com.inspectflow.domain.notification.repositories;

import java.util.Set;
import java.util.UUID;

public interface UserGroupRepository {
    Set<UUID> getMemberIds(UUID groupId);
}
