select P.id, P.description, T.name, T.description from "Team_Projects"
    JOIN "Team" T on T.id = "Team_Projects".team_id
    JOIN "Projects" P on P.id = "Team_Projects".project_id
    order by P.description;

select T.name, count(E.employee_id) eC from "Team" T
                                                LEFT OUTER JOIN "Employee_Team" E on E.team_id = T.id
group by T.id
having count(E.employee_id) > 0
ORDER BY eC;
