

CREATE TYPE order_table_status as ENUM ('OCCUPIED','AVAILABLE','RESERVED');
CREATE TYPE order_status as ENUM ('PENDING','PROCESSING','COMPLETED','CANCELLED','PAID');
CREATE TYPE extent_type as ENUM ('ML','GR');
CREATE TYPE status as ENUM ('ACTIVE','INACTIVE');

CREATE TABLE IF NOT EXISTS category
(
    id         BIGSERIAL PRIMARY KEY,
    name       VARCHAR(512) NOT NULL,
    status     status       NOT NULL,
    created_at TIMESTAMP(0) NOT NULL,
    updated_at TIMESTAMP(0)
);


CREATE TABLE IF NOT EXISTS product
(
    id          uuid PRIMARY KEY,
    name        VARCHAR(512)   NOT NULL,
    ingredient  VARCHAR(2048)  NOT NULL,
    price       NUMERIC(50, 8) NOT NULL,
    status      status         NOT NULL,
    extent      INTEGER        NOT NULL,
    extent_type extent_type    NOT NULL,
    created_at  TIMESTAMP(0)   NOT NULL,
    updated_at  TIMESTAMP(0),
    category_id BIGINT,
    FOREIGN KEY (category_id) REFERENCES category (id)
);


CREATE TABLE IF NOT EXISTS order_table
(
    id                    uuid PRIMARY KEY,
    dining_table_merge_id uuid           NOT NULL,
    status                order_table_status   NOT NULL,
    price                 NUMERIC(50, 8) NOT NULL,
    created_at            TIMESTAMP(0)   NOT NULL,
    updated_at            TIMESTAMP(0)
);

CREATE TABLE IF NOT EXISTS order_item
(
    id         uuid PRIMARY KEY,
    order_id   uuid           NOT NULL,
    product_id uuid           NOT NULL,
    price      NUMERIC(50, 8) NOT NULL,
    status     order_status   NOT NULL,
    created_at TIMESTAMP(0)   NOT NULL,
    updated_at TIMESTAMP(0),
    FOREIGN KEY (order_id) REFERENCES order_table (id),
    FOREIGN KEY (product_id) REFERENCES product (id)
);



CREATE TABLE IF NOT EXISTS dining_table
(
    id         BIGSERIAL PRIMARY KEY,
    merge_id   uuid         NOT NULL,
    status     VARCHAR      NOT NULL,
    size       INT          NOT NULL,
    created_at TIMESTAMP(0) NOT NULL,
    updated_at TIMESTAMP(0)
);



CREATE TABLE IF NOT EXISTS parameter
(
    currency CHAR(3) NOT NULL
);

INSERT INTO category (name, status, created_at, updated_at)
VALUES ('Category 1', 'ACTIVE', CURRENT_TIMESTAMP, NULL),
       ('Category 2', 'INACTIVE', CURRENT_TIMESTAMP, NULL),
       ('Category 3', 'ACTIVE', CURRENT_TIMESTAMP, NULL);


INSERT INTO product (id, name, ingredient, price, status, extent, extent_type, created_at, updated_at, category_id)
VALUES ('550e8400-e29b-41d4-a716-446655440000', 'Product 1', 'Ingredient 1', 10.99, 'ACTIVE', 100, 'ML',
        CURRENT_TIMESTAMP, NULL, (SELECT id FROM category WHERE name = 'Category 1')),
       ('550e8400-e29b-41d4-a716-446655440001', 'Product 2', 'Ingredient 2', 15.99, 'INACTIVE', 200, 'GR',
        CURRENT_TIMESTAMP, NULL, (SELECT id FROM category WHERE name = 'Category 2')),
       ('550e8400-e29b-41d4-a716-446655440002', 'Product 3', 'Ingredient 3', 25.99, 'ACTIVE', 150, 'ML',
        CURRENT_TIMESTAMP, NULL, (SELECT id FROM category WHERE name = 'Category 3'));

INSERT INTO dining_table (merge_id, status, size, created_at, updated_at)
VALUES ('550e8400-e29b-41d4-a716-446655440003', 'OCCUPIED', 4, CURRENT_TIMESTAMP, NULL),
       ('550e8400-e29b-41d4-a716-446655440004', 'AVAILABLE', 2, CURRENT_TIMESTAMP, NULL);

INSERT INTO order_table (id, dining_table_merge_id, status, price, created_at, updated_at)
VALUES ('550e8400-e29b-41d4-a716-446655440005', '550e8400-e29b-41d4-a716-446655440003', 'OCCUPIED', 50.99,
        CURRENT_TIMESTAMP, NULL),
       ('550e8400-e29b-41d4-a716-446655440006', '550e8400-e29b-41d4-a716-446655440004', 'AVAILABLE', 75.99,
        CURRENT_TIMESTAMP, NULL);


INSERT INTO order_item (id, order_id, product_id, price, status, created_at, updated_at)
VALUES ('550e8400-e29b-41d4-a716-446655440007', '550e8400-e29b-41d4-a716-446655440005',
        '550e8400-e29b-41d4-a716-446655440000', 10.99, 'PROCESSING', CURRENT_TIMESTAMP, NULL),
       ('550e8400-e29b-41d4-a716-446655440008', '550e8400-e29b-41d4-a716-446655440005',
        '550e8400-e29b-41d4-a716-446655440001', 15.99, 'PROCESSING', CURRENT_TIMESTAMP, NULL),
       ('550e8400-e29b-41d4-a716-446655440009', '550e8400-e29b-41d4-a716-446655440006',
        '550e8400-e29b-41d4-a716-446655440002', 25.99, 'COMPLETED', CURRENT_TIMESTAMP, NULL);


INSERT INTO parameter (currency)
VALUES ('TRY');
