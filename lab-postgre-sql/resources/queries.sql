select P.id, P.description, T.name, T.description from "Team_Projects"
    JOIN "Team" T on T.id = "Team_Projects".team_id
    JOIN "Projects" P on P.id = "Team_Projects".project_id
    order by P.description;

select T.name, count(E.employee_id) eC from "Team" T
LEFT OUTER JOIN "Employee_Team" E on E.team_id = T.id
group by T.id
having count(E.employee_id) > 0
ORDER BY eC;

WITH RECURSIVE r AS (
    SELECT
        1 AS i,
        1 AS factorial

    UNION
    SELECT
            i+1 AS i,
            factorial * (i+1) as factorial
    FROM r
    WHERE i < 10
)
SELECT * FROM r;

-- все команды для проекта

WITH RECURSIVE r AS (
    SELECT P.id, P.description, T2.name
    from "Projects" P
    join "Team_Projects" TP on P.id = TP.project_id
    JOIN "Team" T2 on T2.id = TP.team_id
    UNION
    SELECT P.id, P.description, T2.name
    from  "Projects" P
    JOIN "Team_Projects" T on P.id = T.project_id
    JOIN "Team" T2 on T2.id = T.team_id
    JOIN r on r.id = p.id
)
SELECT * FROM r;

SELECT P.description, T2.name
from "Projects" P
         join "Team_Projects" TP on P.id = TP.project_id
         JOIN "Team" T2 on T2.id = TP.team_id