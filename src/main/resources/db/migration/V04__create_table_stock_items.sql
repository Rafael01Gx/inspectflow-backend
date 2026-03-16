CREATE TABLE IF NOT EXISTS stock_items (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    type VARCHAR(50) NOT NULL,
    part_category VARCHAR(50) NOT NULL,
    quantity INTEGER NOT NULL,
    supplier_code VARCHAR(255),
    location VARCHAR(255) NOT NULL,
    min_quantity INTEGER
);

--CREATE INDEX CONCURRENTLY idx_stock_items_name ON stock_items (name);
--CREATE INDEX CONCURRENTLY idx_stock_items_supplier_code ON stock_items (supplier_code);