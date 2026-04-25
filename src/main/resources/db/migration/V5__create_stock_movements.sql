CREATE TABLE stock_movements (
    id          BIGSERIAL PRIMARY KEY,
    type        VARCHAR(3)   NOT NULL CHECK (type IN ('IN', 'OUT')),
    quantity    INTEGER      NOT NULL,
    reason      VARCHAR(255),
    date_time   TIMESTAMP    NOT NULL DEFAULT NOW(),
    product_id  BIGINT       NOT NULL,
    user_id     BIGINT       NOT NULL,

    CONSTRAINT fk_movement_product FOREIGN KEY (product_id) REFERENCES products(id),
    CONSTRAINT fk_movement_user    FOREIGN KEY (user_id)    REFERENCES users(id)
);