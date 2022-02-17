CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE admins(
    id  uuid    PRIMARY KEY DEFAULT uuid_generate_v4(),
    created_at  TIMESTAMP   NOT NULL ,
    username    VARCHAR(255)    NOT NULL UNIQUE ,
    email    VARCHAR(255)    NOT NULL UNIQUE,
    password    VARCHAR(255)    NOT NULL
);

CREATE TABLE organizations(
    id  uuid    PRIMARY KEY DEFAULT uuid_generate_v4(),
    created_at  TIMESTAMP    NOT NULL ,
    name    VARCHAR(255)    NOT NULL UNIQUE ,
    phone   VARCHAR(255)    NOT NULL ,
    address VARCHAR(255)    NOT NULL,
    admin_id    uuid    REFERENCES admins(id)   NOT NULL
);
