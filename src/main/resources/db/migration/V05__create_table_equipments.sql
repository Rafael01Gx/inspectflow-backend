CREATE TABLE equipments (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255) NOT NULL,
    code VARCHAR(255) NOT NULL UNIQUE,
    status VARCHAR(50) NOT NULL,
    type VARCHAR(50) NOT NULL,
    location VARCHAR(255) NOT NULL,
    last_inspection TIMESTAMP WITHOUT TIME ZONE,
    next_inspection TIMESTAMP WITHOUT TIME ZONE
);

CREATE TABLE equipment_stock_items (
    equipment_id UUID NOT NULL,
    stock_item_id BIGINT NOT NULL,
    CONSTRAINT pk_equipment_stock_items PRIMARY KEY (equipment_id, stock_item_id),
    CONSTRAINT fk_equipment FOREIGN KEY (equipment_id) REFERENCES equipments(id) ON DELETE CASCADE,
    CONSTRAINT fk_stock_item FOREIGN KEY (stock_item_id) REFERENCES stock_items(id) ON DELETE CASCADE
);
