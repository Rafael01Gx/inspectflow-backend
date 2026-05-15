CREATE TABLE IF NOT EXISTS work_order_attachments
(
    id           UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    type         VARCHAR(50)  NOT NULL,
    file_name    VARCHAR(255) NOT NULL,
    file_url     VARCHAR(255) NOT NULL,
    content_type VARCHAR(255),
    work_order_id UUID         NOT NULL,

    CONSTRAINT fk_work_order_attachment_work_order
        FOREIGN KEY (work_order_id) REFERENCES work_orders (id) ON DELETE CASCADE,


    CONSTRAINT uq_work_order_attachment_type UNIQUE (work_order_id, type)
);