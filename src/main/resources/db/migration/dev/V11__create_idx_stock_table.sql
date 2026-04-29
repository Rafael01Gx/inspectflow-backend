CREATE EXTENSION IF NOT EXISTS pg_trgm;

CREATE INDEX idx_stock_name_trgm ON stock_items USING gin (name gin_trgm_ops);