CREATE TABLE inspection_items (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    status VARCHAR(50) NOT NULL,
    category VARCHAR(50) NOT NULL,
    impediment_item BOOLEAN NOT NULL DEFAULT FALSE,
    observation TEXT,
    checklist_id BIGINT NOT NULL,
    CONSTRAINT fk_checklist FOREIGN KEY (checklist_id) REFERENCES checklists(id) ON DELETE CASCADE
);
