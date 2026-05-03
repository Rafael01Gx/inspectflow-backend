CREATE TABLE IF NOT EXISTS stock_item_usages (
    id            BIGSERIAL PRIMARY KEY,
    stock_item_id BIGINT    NOT NULL,
    work_order_id UUID      NOT NULL,
    quantity_used INTEGER   NOT NULL,
    used_at       TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_stock_item_usage_stock_item
        FOREIGN KEY (stock_item_id) REFERENCES stock_items (id),

    CONSTRAINT fk_stock_item_usage_work_order
        FOREIGN KEY (work_order_id) REFERENCES work_orders (id)
);

CREATE INDEX idx_stock_item_usage_stock_item_id ON stock_item_usages (stock_item_id);
