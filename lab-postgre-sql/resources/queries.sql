select P.id, P.description, T.name, T.description from "Team_Projects"
    JOIN "Team" T on T.id = "Team_Projects".team_id
    JOIN "Projects" P on P.id = "Team_Projects".project_id
    order by P.description

