CREATE KEYSPACE IF NOT EXISTS projects_systems WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 };

CREATE TABLE IF NOT EXISTS projects_systems.projects
(
    id UUID PRIMARY KEY,
    description text,
    last_update_timestamp timestamp
);

INSERT INTO projects_systems.projects
(id, description, last_update_timestamp)
VALUES (uuid(),'test1', toTimeStamp(now()));

SELECT * FROM projects_systems.projects;

CREATE TABLE IF NOT EXISTS projects_systems.rule
(
    id UUID PRIMARY KEY,
    description text,
    name varchar,
    PRIMARY KEY(id)
);

CREATE TABLE projects_systems.rule_projects
(
    rule_id UUID,
    project_id UUID,
    PRIMARY KEY(rule_id, project_id)
);
