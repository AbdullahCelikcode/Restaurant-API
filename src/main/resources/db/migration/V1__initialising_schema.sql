create table if not exists rma_category
(
    id         bigserial
        constraint pk__rma_category__id primary key,
    name       varchar(512) not null,
    status     varchar(20)  not null
        constraint c__rma_category__status check (status in ('ACTIVE', 'INACTIVE', 'DELETED')),
    created_at timestamp(0) not null,
    updated_at timestamp(0)
);


create table if not exists rma_product
(
    id          uuid
        constraint pk__rma_product__id primary key,
    name        varchar(512)   not null,
    ingredient  varchar(2048)  not null,
    price       numeric(50, 8) not null,
    status      varchar(20)    not null
        constraint c__rma_product__status check (status in ('ACTIVE', 'INACTIVE', 'DELETED')),
    extent      integer        not null,
    extent_type varchar(20)    not null
        constraint c__rma_product__extent_type check (extent_type in ('ML', 'GR')),
    category_id bigint         not null
        constraint fk__rma_product__category_id references rma_category (id),
    created_at  timestamp(0)   not null,
    updated_at  timestamp(0)

);


create table if not exists rma_order
(
    id                    uuid
        constraint pk__rma_order__id primary key,
    dining_table_merge_id uuid           not null,
    status                varchar(20)    not null
        constraint c__rma_order__status check (status in ('PENDING', 'PROCESSING', 'COMPLETED', 'CANCELLED', 'PAID')),
    price                 numeric(50, 8) not null,
    created_at            timestamp(3)   not null,
    updated_at            timestamp(3)
);

create table if not exists rma_order_item
(
    id         uuid
        constraint pk__rma_order_item__id primary key,
    order_id   uuid           not null
        constraint fk__rma_order_item__order_id references rma_order (id),
    product_id uuid           not null
        constraint fk__rma_order_item__product_id references rma_product (id),
    price      numeric(50, 8) not null,
    status     varchar(20)    not null
        constraint c__rma_order_item__status check (status in ('UNPAID', 'PAID')),
    created_at timestamp(3)   not null,
    updated_at timestamp(3)

);



create table if not exists rma_dining_table
(
    id         bigserial
        constraint pk__rma_dining_table__id primary key,
    merge_id   uuid         not null,
    status     varchar(20)  not null
        constraint c__rma_dining_table__status check (status in ('OCCUPIED', 'AVAILABLE', 'RESERVED', 'DELETED')),
    size       int          not null,
    created_at timestamp(0) not null,
    updated_at timestamp(0)
);



create table if not exists rma_parameter
(
    id         bigserial
        constraint pk__rma_parameter__id primary key,
    name       varchar(52)  not null,
    definition varchar(52)  not null,
    created_at timestamp(0) not null,
    updated_at timestamp(0)

);

insert into rma_category (name, status, created_at)
values ('category 1', 'ACTIVE', current_timestamp),
       ('category 2', 'INACTIVE', current_timestamp),
       ('category 3', 'ACTIVE', current_timestamp);


insert into rma_product (id, name, ingredient, price, status, extent, extent_type, created_at, category_id)
values ('7fa260e0-8391-4a1f-86ab-f20a4a3808c9', 'product 1', 'ingredient 1', 10.99, 'ACTIVE', 100, 'ML',
        current_timestamp, 1),
       ('5d090f5b-a74d-4886-a398-e150b38249b9', 'product 2', 'ingredient 2', 15.99, 'INACTIVE', 200, 'GR',
        current_timestamp, 2),
       ('ede817d5-4239-43c4-ad27-a73f185ece47', 'product 3', 'ingredient 3', 25.99, 'ACTIVE', 150, 'ML',
        current_timestamp, 3);

insert into rma_dining_table (merge_id, status, size, created_at)
values ('5948b7a4-d02a-4202-9ed9-4286d1140d29', 'OCCUPIED', 4, current_timestamp),
       ('7acd098d-258d-4a24-ae68-dce0d3ec9319', 'AVAILABLE', 2, current_timestamp);


insert into rma_parameter (name, definition, created_at)
values ('Currency', 'TRY', current_timestamp);
