CREATE TABLE inspection_history
(
    id             UUID PRIMARY KEY,
    equipment_id   UUID         NOT NULL,
    inspection_id  UUID         NOT NULL,
    inspector_id   UUID         NOT NULL,
    inspector_name VARCHAR(255) NOT NULL,
    date           TIMESTAMP    NOT NULL,
    category      VARCHAR(50)  NOT NULL,
    status         VARCHAR(50)  NOT NULL
);

CREATE INDEX idx_inspection_history_equipment_date_covering
    ON inspection_history (equipment_id, date DESC)
    INCLUDE (inspector_name, status, category);

CREATE INDEX idx_inspection_history_inspection_id
    ON inspection_history (inspection_id);