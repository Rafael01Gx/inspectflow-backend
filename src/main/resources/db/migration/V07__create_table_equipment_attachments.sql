CREATE TABLE IF NOT EXISTS equipment_attachments (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    type VARCHAR(50) NOT NULL,
    file_name VARCHAR(255) NOT NULL,
    file_url VARCHAR(255) NOT NULL,
    content_type VARCHAR(255),
    equipment_id UUID NOT NULL,

    CONSTRAINT fk_equipment_attachment_equipment FOREIGN KEY (equipment_id) REFERENCES equipments(id) ON DELETE CASCADE,
    CONSTRAINT uq_equipment_attachment_type UNIQUE (equipment_id, type)
);
