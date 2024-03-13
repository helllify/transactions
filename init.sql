DROP DATABASE IF EXISTS bank CASCADE; 
CREATE DATABASE bank;

CREATE TABLE bank.account (
    id SERIAL PRIMARY KEY,
    currency VARCHAR(25) NOT NULL,
    balance DECIMAL(19, 2) NOT NULL,
    updated TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP()
);

INSERT INTO bank.account (id, currency, balance) VALUES
    (1, 'USD', 500.00),
    (2, 'EUR', 500.00),
    (3, 'USD', 500.00),
    (4, 'EUR', 500.00);