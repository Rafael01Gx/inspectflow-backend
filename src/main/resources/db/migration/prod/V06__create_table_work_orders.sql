CREATE TABLE IF NOT EXISTS work_orders (
    id               UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    title            VARCHAR(255) NOT NULL,
    description      TEXT         NOT NULL,
    equipment_name   VARCHAR(255),
    equipment_id     UUID,
    order_status     VARCHAR(50),
    order_priority   VARCHAR(50),
    due_date         DATE         NOT NULL,
    system_info      JSONB,
    assignee_id      UUID         NOT NULL,
    performed_work   TEXT,
    completion_date  DATE,
    created_at       TIMESTAMP,

    CONSTRAINT fk_work_order_equipment
        FOREIGN KEY (equipment_id) REFERENCES equipments (id) ON DELETE SET NULL,

    CONSTRAINT fk_work_order_user
        FOREIGN KEY (assignee_id) REFERENCES users (id) ON DELETE RESTRICT
);

CREATE INDEX IF NOT EXISTS idx_work_orders_assignee_id ON work_orders (assignee_id);
CREATE INDEX IF NOT EXISTS idx_work_orders_status ON work_orders(order_status);
CREATE INDEX IF NOT EXISTS idx_work_orders_priority ON work_orders(order_priority);
CREATE INDEX IF NOT EXISTS idx_work_orders_created_at ON work_orders(created_at);
CREATE INDEX IF NOT EXISTS idx_work_orders_completion_date ON work_orders(completion_date);
CREATE INDEX IF NOT EXISTS idx_work_orders_equipment_name_lower ON work_orders (LOWER(equipment_name));

-- Tabela de peças vinculadas a uma ordem de serviço
CREATE TABLE IF NOT EXISTS work_order_parts (
    work_order_id UUID         NOT NULL,
    stock_id      BIGINT,
    name          VARCHAR(255) NOT NULL,
    quantity      INTEGER      NOT NULL,
    is_from_stock BOOLEAN      NOT NULL,

    CONSTRAINT fk_work_order_parts_work_order
        FOREIGN KEY (work_order_id) REFERENCES work_orders (id) ON DELETE CASCADE
);