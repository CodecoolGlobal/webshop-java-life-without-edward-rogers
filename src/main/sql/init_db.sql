DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS product_category;
DROP TABLE IF EXISTS suppliers;

CREATE TABLE suppliers
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL
);

CREATE TABLE product_category
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL
);


CREATE TABLE products
(
    id                  SERIAL PRIMARY KEY,
    name                VARCHAR    NOT NULL,
    description         VARCHAR,
    default_price       FLOAT      NOT NULL,
    currency            VARCHAR(3) NOT NULL,
    supplier_id         INTEGER REFERENCES suppliers (id),
    product_category_id INTEGER REFERENCES product_category (id),
    picture             VARCHAR
);



