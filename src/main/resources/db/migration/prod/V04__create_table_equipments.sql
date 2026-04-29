CREATE TABLE IF NOT EXISTS equipments (
    id                   UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name                 VARCHAR(255) NOT NULL,
    code                 VARCHAR(255) NOT NULL UNIQUE,
    status               VARCHAR(50)  NOT NULL,
    type                 VARCHAR(50)  NOT NULL,
    location             VARCHAR(255) NOT NULL,
    checklist_id         VARCHAR(255),
    inspection_frequency INT          NOT NULL DEFAULT 7,
    last_inspection      TIMESTAMP WITHOUT TIME ZONE,
    next_inspection      TIMESTAMP WITHOUT TIME ZONE,
    image_url            VARCHAR(255),
    property_code        VARCHAR(100)
);

CREATE INDEX IF NOT EXISTS idx_equipments_name ON equipments (name);
CREATE INDEX IF NOT EXISTS idx_equipment_code  ON equipments (code);

-- Tabela de relação N:N entre equipments e stock_items
CREATE TABLE IF NOT EXISTS equipment_stock_items (
    equipment_id  UUID   NOT NULL,
    stock_item_id BIGINT NOT NULL,
    CONSTRAINT pk_equipment_stock_items  PRIMARY KEY (equipment_id, stock_item_id),
    CONSTRAINT fk_equipment_stock_equip FOREIGN KEY (equipment_id)  REFERENCES equipments(id)   ON DELETE CASCADE,
    CONSTRAINT fk_equipment_stock_item  FOREIGN KEY (stock_item_id) REFERENCES stock_items(id)  ON DELETE CASCADE
);
