ALTER TABLE work_orders
    ALTER COLUMN completion_date TYPE TIMESTAMP
        USING completion_date::TIMESTAMP;