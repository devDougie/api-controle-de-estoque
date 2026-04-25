CREATE TABLE products (
    id            BIGSERIAL PRIMARY KEY,
    name          VARCHAR(150)   NOT NULL,
    sku           VARCHAR(50)    NOT NULL UNIQUE,
    description   VARCHAR(255),
    price         NUMERIC(10, 2) NOT NULL,
    quantity      INTEGER        NOT NULL DEFAULT 0,
    minimum_stock INTEGER        NOT NULL DEFAULT 0,
    category_id   BIGINT         NOT NULL,
    supplier_id   BIGINT,

    CONSTRAINT fk_product_category FOREIGN KEY (category_id) REFERENCES categories(id),
    CONSTRAINT fk_product_supplier FOREIGN KEY (supplier_id) REFERENCES suppliers(id)
);