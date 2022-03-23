import helpers.DBHelper;

import java.sql.*;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        if(args.length == 0) {
            return;
        }

        Properties props = new Properties();
        props.setProperty("user","postgres");
        props.setProperty("password","299792458");
        props.setProperty("ssl","false");

        try(var connection = DriverManager.getConnection(args[0], props)) {
            var statement = connection.createStatement();

            var resultSet = statement.executeQuery(
                    """
                            select P.id,  T.name, P.description, T.description from projects_systems.public."Team_Projects" TP
                                JOIN projects_systems.public."Team" T on T.id = TP.team_id
                                JOIN projects_systems.public."Projects" P on P.id = TP.project_id
                                order by P.description"""
            );
            printFirstQueryResult(resultSet);

            insertOperations(connection);
            System.out.println("\nОсуществляется выход...");
            statement.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void insertOperations(Connection connection) throws SQLException {
        var projectName = "Generated Pr " + Math.exp(Math.random());
        var description = "Generated Description " + Math.exp(Math.random());

        var statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

        var projectsInsert = connection.prepareStatement("INSERT INTO  projects_systems.public.\"Projects\" (description) VALUES (?)");
        var teamInsert = connection.prepareStatement("INSERT INTO  projects_systems.public.\"Team\" (name, description) VALUES (?, ?)");
        var teamProjectsInsert = connection.prepareStatement("INSERT INTO  projects_systems.public.\"Team_Projects\" (project_id, team_id) VALUES (?, ?)");
        var employeeProjectsInsert = connection.prepareStatement("INSERT INTO  projects_systems.public.\"Employee\" (name, start_date, position_id, data) VALUES (?, '2001.12.11', ?, 'No data')");
        var categoriesInsert = connection.prepareStatement("INSERT INTO  projects_systems.public.\"P_Categories\" (name, description) VALUES (?, ?)");
        var userInsert = connection.prepareStatement("INSERT INTO  projects_systems.public.\"User\" (name, employee_id, credentials, data) VALUES (?, ?, ?, ?)");
        var employeeTeamInsert = connection.prepareStatement("INSERT INTO  projects_systems.public.\"Employee_Team\" (employee_id, team_id, role_id, start_date) VALUES (?, ?, ?, ?)");
        var categoriesProjectsInsert = connection.prepareStatement("INSERT INTO  projects_systems.public.\"P_Categories_Projects\" (project_id, p_category_id) VALUES (?, ?)");
        var employeeSkillInsert = connection.prepareStatement("INSERT INTO  projects_systems.public.\"Employee_Skill\" (employee_id, skill_id, level) VALUES (?, ?, ?)");
        var skillInsert = connection.prepareStatement("INSERT INTO  projects_systems.public.\"Skill\" (name, description) VALUES (?, ?)");
        var historyInsert = connection.prepareStatement("INSERT INTO  projects_systems.public.\"E_History\" (employee_id, description, change_date) VALUES (?, ?, ?)");
        var positionInsert = connection.prepareStatement("INSERT INTO  projects_systems.public.\"Position\" (name, description) VALUES (?, ?)");
        var roleInsert = connection.prepareStatement("INSERT INTO  projects_systems.public.\"Role\" (name, description) VALUES (?, ?)");
        var ruleInsert = connection.prepareStatement("INSERT INTO  projects_systems.public.\"Rule\" (name, description) VALUES (?, ?)");
        var repositoryInsert = connection.prepareStatement("INSERT INTO  projects_systems.public.\"Repository\" (link, description) VALUES (?, ?)");
        var repositoryProjectsInsert = connection.prepareStatement("INSERT INTO  projects_systems.public.\"Repository_Projects\" (project_id, repository_id) VALUES (?, ?)");
        var ruleProjectsInsert = connection.prepareStatement("INSERT INTO  projects_systems.public.\"Rule_Projects\" (rule_id, project_id) VALUES (?, ?)");


        projectsInsert.setString(1, projectName);
        projectsInsert.executeQuery();

        teamInsert.setString(1, DBHelper.generateName());
        teamInsert.setString(2, DBHelper.generateDescription());
        teamInsert.executeQuery();


//        System.out.println("\nДобавление " + artistName);
//        projectsInsert.setString(1, artistName);
//        projectsInsert.executeUpdate();
//        printArtistTable(statement.executeQuery("SELECT * FROM ARTIST"));

//        System.out.println("\nОбновление " + artistName + "->" + artistNextName);
//        preparedUpdateStatement.setString(1, artistNextName);
//        preparedUpdateStatement.setString(2, artistName);
//        preparedUpdateStatement.executeUpdate();
//        printArtistTable(statement.executeQuery("SELECT * FROM ARTIST"));
//
//        System.out.println("\nУдаление " + artistNextName);
//        preparedDeleteStatement.setString(1, artistNextName);
//        preparedDeleteStatement.executeUpdate();
//        printArtistTable(statement.executeQuery("SELECT * FROM ARTIST"));
    }

    private static void printFirstQueryResult(ResultSet resultSet) {
        System.out.format("%8s\t%18s\t%18s\t%18s\n", "ID", "Team Name", "Project Name", "T. Description");
        System.out.format(
                "%8s\t%18s\t%18s\t%18s\n",
                "________",
                "__________________",
                "__________________",
                "__________________"
        );
        try {
            while (resultSet.next()) {
                System.out.format(
                        "%8s\t%18s\t%18s\t%18s\n",
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4)
                );
                System.out.println();
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    private static void printQueryResult(ResultSet resultSet) {
        System.out.format("%12s\t%20s\n", "Album Name", "Duration");
        System.out.format(
                "%20s\t%20s\n",
                "____________________",
                "____________________"
        );
        try {
            while (resultSet.next()) {
                System.out.format(
                        "%20s\t%20s\n",
                        resultSet.getString(1),
                        resultSet.getTime(2).toString()
                );
                System.out.println();
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }
}
