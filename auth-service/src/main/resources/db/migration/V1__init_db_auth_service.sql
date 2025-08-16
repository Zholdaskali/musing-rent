-- init Database auth-service (Микросервиса) --

CREATE TABLE t_users (
    id              UUID            NOT NULL,                   -- auth_id
    username        VARCHAR(50)     NOT NULL    UNIQUE,
    email           VARCHAR(100)    NOT NULL    UNIQUE,
    password_hash   TEXT            NOT NULL,
    create_at       TIMESTAMP       NOT NULL    DEFAULT NOW(),
    update_at       TIMESTAMP       NOT NULL    DEFAULT NOW(),
    PRIMARY KEY (id)
);

CREATE TABLE t_refresh_tokens (
    id              UUID            NOT NULL,
    auth_id         UUID            NOT NULL,
    token           TEXT            NOT NULL,
    expires_at      TIMESTAMP       NOT NULL,
    create_at       TIMESTAMP       NOT NULL    DEFAULT NOW(),
    PRIMARY KEY (id),
    FOREIGN KEY (auth_id) REFERENCES t_users(id)
);