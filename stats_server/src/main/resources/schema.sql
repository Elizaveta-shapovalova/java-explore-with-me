DROP TABLE IF EXISTS hits;
DROP TABLE IF EXISTS apps;


CREATE TABLE IF NOT EXISTS apps (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY NOT NULL,
    name VARCHAR(64) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS hits (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY NOT NULL,
    app_id BIGINT NOT NULL REFERENCES apps (id) ON DELETE CASCADE,
    uri VARCHAR(64),
    ip VARCHAR(16),
    timestamp TIMESTAMP NOT NULL
);

