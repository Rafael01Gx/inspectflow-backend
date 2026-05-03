package br.com.inspectflow.domain.notification.models;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "notification_group_members")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationGroupMember {

    @EmbeddedId
    private NotificationGroupMemberId id;

    @Column(name = "added_at", nullable = false, updatable = false)
    private Instant addedAt = Instant.now();

    @Embeddable
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode(callSuper = false)
    public static class NotificationGroupMemberId implements Serializable {

        @Column(name = "group_id")
        private UUID groupId;

        @Column(name = "user_id")
        private UUID userId;

    }

    public UUID getGroupId() { return id.groupId; }
    public UUID getUserId()  { return id.userId; }
}