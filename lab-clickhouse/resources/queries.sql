CREATE DATABASE projects_systems;

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

CREATE TABLE IF NOT EXISTS projects_systems.e_History
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

