CREATE TABLE equipment_consignment_codes (
    equipment_id UUID NOT NULL,
    consignment_value VARCHAR(255),
    consignment_key VARCHAR(255) NOT NULL,

    PRIMARY KEY (equipment_id, consignment_key),

    CONSTRAINT fk_equipment_consignment
        FOREIGN KEY (equipment_id)
        REFERENCES equipments (id)
        ON DELETE CASCADE
);