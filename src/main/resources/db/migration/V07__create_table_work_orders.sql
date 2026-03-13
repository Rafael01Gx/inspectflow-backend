CREATE TABLE work_orders (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    title VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    equipment_name VARCHAR(255),
    equipment_id UUID,
    order_status VARCHAR(50),
    order_priority VARCHAR(50),
    due_date DATE NOT NULL,
    assignee VARCHAR(255) NOT NULL,
    performed_work TEXT NOT NULL,
    completion_date DATE,
    CONSTRAINT fk_work_order_equipment FOREIGN KEY (equipment_id) REFERENCES equipments(id) ON DELETE SET NULL
);

CREATE TABLE work_order_parts (
    work_order_id UUID NOT NULL,
    stock_id BIGINT,
    name VARCHAR(255) NOT NULL,
    quantity INTEGER NOT NULL,
    is_from_stock BOOLEAN NOT NULL,
    CONSTRAINT fk_work_order_parts_work_order FOREIGN KEY (work_order_id) REFERENCES work_orders(id) ON DELETE CASCADE
);
