CREATE INDEX IF NOT EXISTS idx_work_orders_assignee_id ON work_orders (assignee_id);
CREATE INDEX IF NOT EXISTS idx_work_orders_status ON work_orders(order_status);
CREATE INDEX IF NOT EXISTS idx_work_orders_priority ON work_orders(order_priority);
CREATE INDEX IF NOT EXISTS idx_work_orders_created_at ON work_orders(created_at);
CREATE INDEX IF NOT EXISTS idx_work_orders_completion_date ON work_orders(completion_date);
CREATE INDEX IF NOT EXISTS idx_work_orders_equipment_name_lower ON work_orders (LOWER(equipment_name));