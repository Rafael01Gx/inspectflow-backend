CREATE EXTENSION IF NOT EXISTS "pgcrypto";

-- Grupos de notificação
CREATE TABLE notification_groups (
                                     id          UUID        PRIMARY KEY DEFAULT gen_random_uuid(),
                                     name        VARCHAR(100) NOT NULL,
                                     description TEXT,
                                     linked_role VARCHAR(50),
                                     created_at  TIMESTAMPTZ NOT NULL DEFAULT now()
);

-- Membros dos grupos
CREATE TABLE notification_group_members (
                                            group_id   UUID NOT NULL REFERENCES notification_groups(id) ON DELETE CASCADE,
                                            user_id    UUID NOT NULL,
                                            added_at   TIMESTAMPTZ NOT NULL DEFAULT now(),
                                            PRIMARY KEY (group_id, user_id)
);

-- Tabela principal de notificações
CREATE TABLE notifications (
                               id           UUID         PRIMARY KEY DEFAULT gen_random_uuid(),
                               recipient_id UUID         NOT NULL,
                               group_id     UUID         REFERENCES notification_groups(id) ON DELETE SET NULL,
                               type         VARCHAR(20)  NOT NULL CHECK (type IN ('INFO','WARNING','ERROR','SUCCESS','ALERT')),
                               title        VARCHAR(255) NOT NULL,
                               message      TEXT         NOT NULL,
                               metadata     JSONB,
                               read         BOOLEAN      NOT NULL DEFAULT false,
                               read_at      TIMESTAMPTZ,
                               created_at   TIMESTAMPTZ  NOT NULL DEFAULT now(),
                               expires_at   TIMESTAMPTZ
);

-- Índices para queries frequentes
CREATE INDEX idx_notifications_recipient_unread
    ON notifications (recipient_id, read, created_at DESC)
    WHERE read = false;

CREATE INDEX idx_notifications_recipient_created
    ON notifications (recipient_id, created_at DESC);

CREATE INDEX idx_notifications_group
    ON notifications (group_id)
    WHERE group_id IS NOT NULL;

CREATE INDEX idx_notifications_metadata
    ON notifications USING GIN (metadata)
    WHERE metadata IS NOT NULL;
