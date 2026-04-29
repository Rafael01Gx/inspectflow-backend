CREATE TABLE IF NOT EXISTS equipment_components (
    id           UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name         VARCHAR(255) NOT NULL,
    category     VARCHAR(50)  NOT NULL,
    equipment_id UUID,

    CONSTRAINT fk_equipment_component_equipment
        FOREIGN KEY (equipment_id) REFERENCES equipments (id) ON DELETE CASCADE
);

-- =============================================================
-- Tabela: inspection_items
-- Depende de: equipment_components
-- =============================================================

CREATE TABLE IF NOT EXISTS inspection_items (
    id                     BIGSERIAL PRIMARY KEY,
    title                  VARCHAR(255) NOT NULL,
    description            TEXT         NOT NULL,
    category               VARCHAR(50)  NOT NULL,
    impediment_item        BOOLEAN      NOT NULL DEFAULT FALSE,
    equipment_component_id UUID         NOT NULL,

    CONSTRAINT fk_inspection_item_component
        FOREIGN KEY (equipment_component_id) REFERENCES equipment_components (id) ON DELETE CASCADE
);
