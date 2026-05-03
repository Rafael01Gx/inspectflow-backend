CREATE TABLE IF NOT EXISTS password_reset_token (
    id          BIGSERIAL    PRIMARY KEY,
    token       VARCHAR(255) NOT NULL,
    user_id     UUID         NOT NULL,
    expiry_date BIGINT       NOT NULL,

    CONSTRAINT uk_token              UNIQUE (token),
    CONSTRAINT fk_user_password_reset
        FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE INDEX idx_password_token ON password_reset_token (token);
CREATE INDEX idx_expiry_date    ON password_reset_token (expiry_date);
