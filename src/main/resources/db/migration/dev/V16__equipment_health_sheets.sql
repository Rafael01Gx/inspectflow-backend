ALTER TABLE equipments DROP COLUMN IF EXISTS inspection_frequency, DROP COLUMN IF EXISTS last_inspection, DROP COLUMN IF EXISTS next_inspection;

CREATE TABLE IF NOT EXISTS equipment_health_sheets (
    id                               UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    equipment_id                     UUID NOT NULL UNIQUE,

    mechanical_inspection_frequency  VARCHAR(50),
    last_mechanical_inspection       TIMESTAMP WITHOUT TIME ZONE,
    next_mechanical_inspection       TIMESTAMP WITHOUT TIME ZONE,

    electrical_inspection_frequency  VARCHAR(50),
    last_electrical_inspection       TIMESTAMP WITHOUT TIME ZONE,
    next_electrical_inspection       TIMESTAMP WITHOUT TIME ZONE,

    calibration_inspection_frequency VARCHAR(50),
    last_calibration                 TIMESTAMP WITHOUT TIME ZONE,
    next_calibration                 TIMESTAMP WITHOUT TIME ZONE,

    CONSTRAINT fk_health_sheet_equipment FOREIGN KEY (equipment_id) REFERENCES equipments(id) ON DELETE CASCADE
    );


CREATE INDEX idx_health_next_mech ON equipment_health_sheets(next_mechanical_inspection);
CREATE INDEX idx_health_next_elec ON equipment_health_sheets(next_electrical_inspection);
CREATE INDEX idx_health_next_calib ON equipment_health_sheets(next_calibration);