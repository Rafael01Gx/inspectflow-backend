CREATE INDEX idx_health_overdue ON equipment_health_sheets (
                                                            next_mechanical_inspection, next_electrical_inspection, next_calibration
    );

CREATE INDEX idx_wo_assignee_created ON work_orders (assignee_id, created_at DESC);
CREATE INDEX idx_wo_assignee_completion ON work_orders (assignee_id, completion_date DESC);

CREATE INDEX idx_stock_usage_item_date ON stock_item_usages (stock_item_id, used_at DESC);