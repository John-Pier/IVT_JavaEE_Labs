CREATE DATABASE projects_systems;

CREATE USER IF NOT EXISTS user_db IDENTIFIED WITH PLAINTEXT_PASSWORD BY '1234';

GRANT ALL PRIVILEGES ON projects_systems.* TO user_db;

CREATE TABLE IF NOT EXISTS projects_systems.projects
(
    id UUID DEFAULT generateUUIDv4(),
    description String,
    PRIMARY KEY(id)
) ENGINE = MergeTree;

CREATE TABLE IF NOT EXISTS projects_systems.rule
(
    id UUID DEFAULT generateUUIDv4(),
    description String,
    name String NOT NULL,
    PRIMARY KEY(id)
) ENGINE = MergeTree;

CREATE TABLE projects_systems.rule_projects
(
    rule_id UUID NOT NULL,
    project_id UUID NOT NULL,
    PRIMARY KEY(rule_id, project_id)
)  ENGINE = MergeTree;

CREATE TABLE IF NOT EXISTS projects_systems.employee
(
    id UUID DEFAULT generateUUIDv4(),
    name String NOT NULL,
    start_date Date DEFAULT toDate(now()),
    position_id UUID NOT NULL,
    data String,
    PRIMARY KEY (id)
) ENGINE = MergeTree;

CREATE TABLE IF NOT EXISTS projects_systems.e_history
(
    id UUID DEFAULT generateUUIDv4(),
    employee_id UUID NOT NULL,
    description String,
    change_date Date DEFAULT toDate(now()),
    PRIMARY KEY(id)
) ENGINE = MergeTree;

CREATE TABLE IF NOT EXISTS projects_systems.employee_skill
(
    employee_id UUID NOT NULL,
    skill_id UUID NOT NULL,
    level String DEFAULT '1',
    PRIMARY KEY(employee_id, skill_id)
) ENGINE = MergeTree;

CREATE TABLE IF NOT EXISTS projects_systems.employee_team
(
    employee_id UUID NOT NULL,
    team_id UUID NOT NULL,
    role_id UUID NOT NULL,
    start_date Date DEFAULT toDate(now()),
    PRIMARY KEY(employee_id, team_id)
) ENGINE = MergeTree;

CREATE TABLE IF NOT EXISTS projects_systems.p_categories
(
    id UUID DEFAULT generateUUIDv4(),
    name String NOT NULL,
    description String,
    PRIMARY KEY(id)
) ENGINE = MergeTree;

CREATE TABLE IF NOT EXISTS projects_systems.p_categories_projects
(
    project_id UUID NOT NULL,
    p_category_id UUID NOT NULL,
    PRIMARY KEY(project_id, p_category_id)
) ENGINE = MergeTree;

CREATE TABLE IF NOT EXISTS projects_systems.position
(
    id UUID DEFAULT generateUUIDv4(),
    name String NOT NULL,
    description String,
    PRIMARY KEY(id)
) ENGINE = MergeTree;

CREATE TABLE IF NOT EXISTS projects_systems.repository
(
    id UUID DEFAULT generateUUIDv4(),
    link String NOT NULL,
    description String,
    PRIMARY KEY(id)
) ENGINE = MergeTree;

CREATE TABLE IF NOT EXISTS projects_systems.repository_projects
(
    project_id UUID NOT NULL,
    repository_id UUID NOT NULL,
    PRIMARY KEY(project_id, repository_id)
) ENGINE = MergeTree;

CREATE TABLE IF NOT EXISTS projects_systems.role
(
    id UUID DEFAULT generateUUIDv4(),
    name String NOT NULL,
    description String,
    PRIMARY KEY (id)
) ENGINE = MergeTree;

CREATE TABLE IF NOT EXISTS projects_systems.skill
(
    id UUID DEFAULT generateUUIDv4(),
    name String NOT NULL,
    description String,
    PRIMARY KEY (id)
) ENGINE = MergeTree;

CREATE TABLE IF NOT EXISTS projects_systems.team
(
    id UUID DEFAULT generateUUIDv4(),
    name String NOT NULL,
    description String,
    PRIMARY KEY (id)
) ENGINE = MergeTree;

CREATE TABLE IF NOT EXISTS projects_systems.team_projects
(
    project_id UUID NOT NULL,
    team_id UUID NOT NULL,
    PRIMARY KEY (project_id, team_id)
) ENGINE = MergeTree;

CREATE TABLE IF NOT EXISTS projects_systems.user
(
    id UUID DEFAULT generateUUIDv4(),
    name String NOT NULL,
    employee_id UUID NOT NULL,
    credentials String NOT NULL,
    data String,
    parent_id UUID NULL,
    PRIMARY KEY (id)
) ENGINE = MergeTree;

INSERT INTO projects_systems.projects(description) values  ('Core Project'), ('Structure'), ('NRM System'), ('HR Platform')

USE projects_systems;

INSERT INTO projects_systems.team(name, description) values  ('Core Project', '-'), ('Structure', '-'), ('NRM System', '-'), ('HR Platform', '-');

INSERT INTO projects_systems.team (* EXCEPT(id)) VALUES ('Core Project', '-');

select T.name as team_name, count(E.employee_id) eC from projects_systems.team T
LEFT OUTER JOIN projects_systems.employee_team E on E.team_id = T.id
group by (T.id, team_name)
having count(E.employee_id) > 0
ORDER BY eC DESC


WITH sum(bytes) as s
SELECT
    formatReadableSize(s),
    table
FROM system.parts
GROUP BY table
ORDER BY s;

WITH R AS (
    SELECT U.id, U.name, U.parent_id
    from projects_systems.user U
    where U.id = 'e7be94ce-fef0-4f1a-8074-af984cf5e910'
    UNION all
    SELECT U.id, U.name, U.parent_id
    from projects_systems.user U
             JOIN projects_systems.user U1 on U1.id = U.parent_id
)
SELECT * FROM R;