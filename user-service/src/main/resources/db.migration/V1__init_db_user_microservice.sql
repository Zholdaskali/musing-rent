-- init Database user-service (Микросервиса) --

CREATE TABLE t_user_profiles (
    id              UUID            DEFAULT,
    auth_id         UUID            NOT NULL,                   -- auth_id приходит из Auth-service
    full_name       VARCHAR(100),
    avatar_url      TEXT,
    created_at      TIMESTAMP       NOT NULL DEFAULT NOW(),
    updated_at      TIMESTAMP       NOT NULL DEFAULT NOW(),
    PRIMARY KEY (id)
);

-- Таблица адресов или других пользовательских данных
CREATE TABLE t_user_addresses (
    id              UUID            DEFAULT,
    auth_id         UUID            NOT NULL,                   -- auth_id пользователя в микросервисе auth подробнее в One-note
    street          VARCHAR(255),
    city            VARCHAR(100),
    country         VARCHAR(50),
    created_at      TIMESTAMP       NOT NULL DEFAULT NOW(),
    PRIMARY KEY (id)
);