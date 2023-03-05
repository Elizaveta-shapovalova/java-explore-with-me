DROP TABLE IF EXISTS users, categories, compilations, locations, events, requests, compilation_event, payments;

CREATE TABLE IF NOT EXISTS users
(
    id    BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY NOT NULL,
    name  VARCHAR(512)                                        NOT NULL,
    email VARCHAR(512)                                        NOT NULL,
    CONSTRAINT UQ_USER_EMAIL UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS categories
(
    id   BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY NOT NULL,
    name VARCHAR(64)                                         NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS compilations
(
    id     BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY NOT NULL,
    title  VARCHAR(512)                                        NOT NULL,
    pinned BOOLEAN                                             NOT NULL
);

CREATE TABLE IF NOT EXISTS locations
(
    id  BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY NOT NULL,
    lat INTEGER                                             NOT NULL,
    lon INTEGER                                             NOT NULL
);

CREATE TABLE IF NOT EXISTS events
(
    id                 BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    annotation         VARCHAR(2000)               NOT NULL,
    category_id        BIGINT REFERENCES categories (id) ON DELETE CASCADE,
    created_on         TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    description        VARCHAR(7000),
    event_date         TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    initiator_id       BIGINT REFERENCES users (id) ON DELETE CASCADE,
    location_id        BIGINT REFERENCES locations (id) ON DELETE CASCADE,
    paid               BOOLEAN                     NOT NULL,
    price              DOUBLE PRECISION,
    participant_limit  BIGINT                      NOT NULL,
    published_on       TIMESTAMP WITHOUT TIME ZONE,
    request_moderation BOOLEAN                     NOT NULL,
    state              VARCHAR(64)                 NOT NULL,
    title              VARCHAR(120)                NOT NULL
);

CREATE TABLE IF NOT EXISTS requests
(
    id           BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY NOT NULL,
    created      TIMESTAMP WITHOUT TIME ZONE                         NOT NULL,
    event_id     BIGINT                                              NOT NULL REFERENCES events (id) ON DELETE CASCADE,
    requester_id BIGINT                                              NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    status       VARCHAR(16)                                         NOT NULL,
    is_paid      BOOLEAN                                             NOT NULL
);

CREATE TABLE IF NOT EXISTS compilation_event
(
    compilation_id BIGINT REFERENCES compilations (id) ON DELETE CASCADE,
    event_id       BIGINT REFERENCES events (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS payments
(
    id         BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY NOT NULL,
    token      VARCHAR(32)                                         NOT NULL,
    status     VARCHAR(32)                                         NOT NULL,
    request_id BIGINT                                              NOT NULL REFERENCES requests (id) ON DELETE CASCADE
);