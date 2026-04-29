CREATE TABLE IF NOT EXISTS equipment_components (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255) NOT NULL,
    category VARCHAR(50) NOT NULL,
    equipment_id UUID,

    CONSTRAINT fk_equipment_component_equipment FOREIGN KEY (equipment_id) REFERENCES equipments(id) ON DELETE CASCADE
);
