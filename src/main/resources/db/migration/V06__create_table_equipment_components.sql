CREATE TABLE equipment_components (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255) NOT NULL,
    category VARCHAR(50) NOT NULL,
    checklist_id BIGINT NOT NULL,
    equipment_id UUID,
    CONSTRAINT fk_equipment_component_equipment FOREIGN KEY (equipment_id) REFERENCES equipments(id) ON DELETE SET NULL,
    CONSTRAINT fk_equipment_component_checklist FOREIGN KEY (checklist_id) REFERENCES checklists(id) ON DELETE CASCADE
);
