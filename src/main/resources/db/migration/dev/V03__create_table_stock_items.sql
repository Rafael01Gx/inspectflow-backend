CREATE TABLE IF NOT EXISTS stock_items (
    id            BIGSERIAL PRIMARY KEY,
    name          VARCHAR(255) NOT NULL,
    type          VARCHAR(50)  NOT NULL,
    part_category VARCHAR(50)  NOT NULL,
    quantity      INTEGER      NOT NULL,
    supplier_code VARCHAR(255),
    location      VARCHAR(255) NOT NULL,
    min_quantity  INTEGER,
    image_url     VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Índice GIN com suporte a busca por similaridade, sem acento e case-insensitive
-- Depende da extensão pg_trgm, unaccent e da função immutable_unaccent (V01)
CREATE INDEX idx_stock_name_trgm
    ON stock_items
    USING gin (immutable_unaccent(lower(name)) gin_trgm_ops);
