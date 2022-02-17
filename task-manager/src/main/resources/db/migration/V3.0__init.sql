CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE tasks(
    id  uuid    PRIMARY KEY DEFAULT uuid_generate_v4(),
    created_at  TIMESTAMP   NOT NULL ,
    title    VARCHAR(255)    NOT NULL ,
    description    VARCHAR(255)    NOT NULL ,
    created_by    uuid    NOT NULL,
    status    VARCHAR(255)    NOT NULL,
    organization_id  uuid    NOT NULL,
    deadline    TIMESTAMP  NOT NULL
);

CREATE TABLE assignees(
    id  uuid    PRIMARY KEY,
    task_id  uuid    references tasks(id) NOT NULL
);