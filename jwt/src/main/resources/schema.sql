DROP TABLE IF EXISTS jwt_user;

CREATE TABLE jwt_user (
    id          BIGINT  NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    username    TEXT    NOT NULL,
    password    TEXT    NOT NULL
);