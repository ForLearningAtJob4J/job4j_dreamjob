CREATE TABLE IF NOT EXISTS post
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(1024),
    description TEXT,
    created     TIMESTAMP DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS candidate
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(1024)
);

CREATE TABLE IF NOT EXISTS "user"
(
    id       SERIAL PRIMARY KEY,
    name     VARCHAR(1024),
    email    VARCHAR(1024) UNIQUE,
    password VARCHAR(32)
);

INSERT INTO "user" (name, email, password)
VALUES ('Admin', 'root@local', 'root')
ON CONFLICT (email) DO UPDATE SET password = 'root';