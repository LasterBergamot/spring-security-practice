DROP TABLE IF EXISTS oauth2_user;

CREATE TABLE oauth2_user (
    id                  BIGINT  NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name                TEXT    NOT NULL,
    email               TEXT    NOT NULL,
    unique_identifier   TEXT    NOT NULL,
    resource_server     TEXT    NOT NULL
);