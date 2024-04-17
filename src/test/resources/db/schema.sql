CREATE TABLE IF NOT EXISTS rma_category
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    name       VARCHAR(512) NOT NULL,
    status     VARCHAR(20)  NOT NULL CHECK (status IN ('ACTIVE', 'INACTIVE', 'DELETED')),
    created_at TIMESTAMP    NOT NULL,
    updated_at TIMESTAMP
);

