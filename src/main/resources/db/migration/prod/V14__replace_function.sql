CREATE OR REPLACE FUNCTION immutable_unaccent(text)
    RETURNS text
    LANGUAGE sql
    IMMUTABLE
AS $$
SELECT public.unaccent('public.unaccent', $1)
           $$;


DROP INDEX IF EXISTS idx_stock_name_trgm;

CREATE INDEX idx_stock_name_trgm
    ON stock_items
    USING gin (immutable_unaccent(lower(name)) gin_trgm_ops);