DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS product_category;
DROP TABLE IF EXISTS suppliers;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS carts;
DROP TABLE IF EXISTS users_cart;


CREATE TABLE suppliers
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL,
    description VARCHAR
);

CREATE TABLE product_category
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL,
    department VARCHAR,
    description VARCHAR
);

CREATE TABLE users
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL,
    password VARCHAR NOT NULL,
    country VARCHAR,
    city VARCHAR,
    zip int,
    address VARCHAR,
    email VARCHAR,
    phone_number VARCHAR
);


CREATE TABLE products
(
    id                  SERIAL PRIMARY KEY,
    name                VARCHAR    NOT NULL,
    description         VARCHAR,
    default_price       FLOAT      NOT NULL,
    currency            VARCHAR(3) NOT NULL,
    supplier_id         INTEGER REFERENCES suppliers (id) ON DELETE CASCADE,
    product_category_id INTEGER REFERENCES product_category (id) ON DELETE CASCADE,
    picture             VARCHAR
);

CREATE TABLE carts
(
    id SERIAL PRIMARY KEY,
    product_id INTEGER REFERENCES products(id) ON DELETE CASCADE,
    quantity int
);

CREATE TABLE users_cart
(
    id SERIAL PRIMARY KEY,
    user_id INTEGER REFERENCES users(id) ON DELETE CASCADE,
    cart_id INTEGER REFERENCES carts(id) ON DELETE CASCADE
)