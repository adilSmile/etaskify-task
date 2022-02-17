CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE users(
      id  uuid    PRIMARY KEY DEFAULT uuid_generate_v4(),
      created_at  TIMESTAMP   NOT NULL ,
      name    VARCHAR(255)    NOT NULL ,
      surname    VARCHAR(255)    NOT NULL ,
      email    VARCHAR(255)    NOT NULL UNIQUE,
      password    VARCHAR(255)    NOT NULL,
      organization_id  uuid    NOT NULL
);
