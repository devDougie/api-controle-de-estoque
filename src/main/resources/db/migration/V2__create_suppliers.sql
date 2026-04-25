CREATE TABLE suppliers (
    id           BIGSERIAL PRIMARY KEY,
    trade_name   VARCHAR(100) NOT NULL,
    company_name VARCHAR(150),
    cnpj         VARCHAR(14)  NOT NULL UNIQUE,
    phone        VARCHAR(20),
    email        VARCHAR(100)
);