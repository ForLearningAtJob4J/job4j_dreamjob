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
    name VARCHAR(1024),
    city_id integer default 0
);

CREATE TABLE IF NOT EXISTS "user"
(
    id       SERIAL PRIMARY KEY,
    name     VARCHAR(1024),
    email    VARCHAR(1024) UNIQUE,
    password VARCHAR(32)
);

CREATE TABLE IF NOT EXISTS city
(
    id       SERIAL PRIMARY KEY,
    name     VARCHAR(1024)
);

INSERT INTO "user" (name, email, password)
VALUES ('Admin', 'root@local', 'root')
ON CONFLICT (email) DO UPDATE SET password = 'root';

INSERT INTO city (id, name)
VALUES (0, 'City is hidden')
ON CONFLICT (id) DO UPDATE SET name = 'City is hidden';