CREATE KEYSPACE IF NOT EXISTS projects_systems WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 };

-- DROP TABLE projects_systems.repository;

CREATE TABLE IF NOT EXISTS projects_systems.projects
(
    id UUID PRIMARY KEY,
    description text,
    riles_map map<text, text>,
    last_update_timestamp timestamp
) WITH comment = 'All projects with rules';

CREATE TABLE IF NOT EXISTS projects_systems.repository
(
    id UUID PRIMARY KEY,
    link text,
    description text,
    projects_ids LIST<UUID>
) WITH comment = 'All repositories with projects ids';

CREATE TABLE IF NOT EXISTS projects_systems.team
(
    id UUID  PRIMARY KEY,
    name text,
    description text,
    projects_ids LIST<UUID>
) WITH comment = 'All projects with rules';

CREATE TABLE IF NOT EXISTS projects_systems.user
(
    id UUID PRIMARY KEY,
    name text,
    employee_id UUID,
    credentials text,
    data text,
) WITH comment = 'All users';

CREATE TABLE IF NOT EXISTS projects_systems.employee
(
    id UUID PRIMARY KEY,
    name text,
    start_date Date,
    position frozen<position>,
    data text,
) WITH comment = 'All employees with position';

CREATE TYPE IF NOT EXISTS projects_systems.position
(
    name text,
    description text,
);

INSERT INTO projects_systems.projects
(id, description, last_update_timestamp, riles_map)
VALUES (uuid(),'test1', toTimeStamp(now()), { 'name1': 'description1'});

INSERT INTO repository
(id, description, link, projects_ids)
VALUES (uuid(), 'description 223', null, ['74b40c66-53d6-4bd1-a043-2d7454479cb6']);

INSERT INTO employee
(id, name, start_date, position, data)
VALUES (uuid(), 'name r32d', toDate(now()), {name: 'naewme', description: 'descri23ption324'}, 'dsarts');


SELECT * FROM projects_systems.projects;
