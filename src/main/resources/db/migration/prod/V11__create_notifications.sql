CREATE TABLE notification_groups
(
    id          UUID PRIMARY KEY      DEFAULT gen_random_uuid(),
    name        VARCHAR(100) NOT NULL,
    description TEXT,
    linked_role VARCHAR(50),
    created_at  TIMESTAMPTZ  NOT NULL DEFAULT now()
);
CREATE TABLE notification_group_members
(
    group_id UUID        NOT NULL REFERENCES notification_groups (id) ON DELETE CASCADE,
    user_id  UUID        NOT NULL,
    added_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    PRIMARY KEY (group_id, user_id)
);

CREATE TABLE notifications
(
    id           UUID PRIMARY KEY      DEFAULT gen_random_uuid(),
    recipient_id UUID         NOT NULL,
    group_id     UUID         REFERENCES notification_groups (id) ON DELETE SET NULL,
    type         VARCHAR(20)  NOT NULL CHECK (type IN ('INFO', 'WARNING', 'ERROR', 'SUCCESS', 'ALERT')),
    title        VARCHAR(255) NOT NULL,
    message      TEXT         NOT NULL,
    metadata     JSONB,
    read         BOOLEAN      NOT NULL DEFAULT false,
    read_at      TIMESTAMPTZ,
    created_at   TIMESTAMPTZ  NOT NULL DEFAULT now(),
    expires_at   TIMESTAMPTZ
);

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


INSERT INTO notification_groups (id, name, description, linked_role, created_at)
VALUES ('a1b2c3d4-0001-0001-0001-000000000001', 'Administradores', 'Grupo dos administradores do sistema',
        'ADMINISTRADOR', now()),
       ('a1b2c3d4-0002-0002-0002-000000000002', 'Líderes', 'Grupo dos líderes de equipe', 'LIDER', now()),
       ('a1b2c3d4-0003-0003-0003-000000000003', 'Supervisores', 'Grupo dos supervisores', 'SUPERVISOR', now()),
       ('a1b2c3d4-0004-0004-0004-000000000004', 'Gestores', 'Grupo dos gestores', 'GESTOR', now()),
       ('a1b2c3d4-0005-0005-0005-000000000005', 'Eletricistas', 'Grupo dos eletricistas', 'ELETRICISTA', now()),
       ('a1b2c3d4-0006-0006-0006-000000000006', 'Mecânicos', 'Grupo dos mecânicos', 'MECANICO', now()),
       ('a1b2c3d4-0007-0007-0007-000000000007', 'Instrumentação', 'Grupo de Instrumentação', 'INSTRUMENTISTA',
        now());
