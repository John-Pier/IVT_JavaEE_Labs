import helpers.DBHelper;

import java.sql.*;
import java.util.Properties;

public class Main {

    public static DBHelper dbHelper;

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
            dbHelper = new DBHelper(connection);
            // insertOperations(connection);

            // Команды и проекты над которыми они работают
            var resultSet = statement.executeQuery(
                    """
                            select T.name, P.description, T.description from projects_systems.public."Team_Projects" TP
                                JOIN projects_systems.public."Team" T on T.id = TP.team_id
                                JOIN projects_systems.public."Projects" P on P.id = TP.project_id
                                order by P.description"""
            );
            printFirstQueryResult(resultSet);

            // Число человек в командах
            resultSet = statement.executeQuery(
                    """
                           select T.name, count(E.employee_id) eC from projects_systems.public."Team" T
                                LEFT OUTER JOIN projects_systems.public."Employee_Team" E on E.team_id = T.id
                                group by T.id
                                having count(E.employee_id) > 0
                                ORDER BY eC DESC"""
            );
            printSecondQueryResult(resultSet);

            resultSet = statement.executeQuery(
                    """
                          WITH RECURSIVE r AS (
                               SELECT U.id, U.name, U.parent_id
                               from projects_systems.public."User" U
                               where U.id = 1
                               UNION
                               SELECT U.id, U.name, U.parent_id
                               from projects_systems.public."User" U
                               JOIN r on r.id = U.parent_id
                          )
                          SELECT * FROM r;"""
            );
            printThirdQueryResult(resultSet);

            System.out.println("\nОсуществляется выход...");
            statement.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void insertOperations(Connection connection) throws SQLException {
        var projectName = "Product " + Math.exp(Math.random());
        var projectsInsert = connection.prepareStatement(
                "INSERT INTO  projects_systems.public.\"Projects\" (description) VALUES (?)"
        );
        projectsInsert.setString(1, projectName);
        projectsInsert.executeUpdate();
        var projectId = dbHelper.selectFirstProjectId();

        if(projectId == -1) {
            return;
        }

        var teamInsert = connection.prepareStatement(
                "INSERT INTO  projects_systems.public.\"Team\" (name, description) VALUES (?, ?)"
        );
        var name = DBHelper.generateName();
        teamInsert.setString(1, name);
        teamInsert.setString(2, DBHelper.generateDescription());
        teamInsert.executeUpdate();
        var teamId = dbHelper.selectTeamId(name);

        var teamProjectsInsert = connection.prepareStatement(
                "INSERT INTO  projects_systems.public.\"Team_Projects\" (project_id, team_id) VALUES (?, ?)"
        );
        teamProjectsInsert.setInt(1, projectId);
        teamProjectsInsert.setInt(2, teamId);
        teamProjectsInsert.executeUpdate();

        var positionName =  DBHelper.generateName();
        var positionInsert = connection.prepareStatement(
                "INSERT INTO  projects_systems.public.\"Position\" (name, description) VALUES (?, ?)"
        );
        positionInsert.setString(1, positionName);
        positionInsert.setString(2, DBHelper.generateDescription());
        positionInsert.executeUpdate();
        var positionId =  dbHelper.selectPositionId(positionName);

        if(positionId == -1) {
            return;
        }

        var employeeName =  DBHelper.generateName();
        var employeeInsert = connection.prepareStatement(
                "INSERT INTO  projects_systems.public.\"Employee\" (name, start_date, position_id, data) VALUES (?, '2001.12.11', ?, 'No data')"
        );
        employeeInsert.setString(1, employeeName);
        employeeInsert.setInt(2, positionId);
        employeeInsert.executeUpdate();
        var employeeId = dbHelper.selectEmployeeId(employeeName);

        var userInsert = connection.prepareStatement(
                "INSERT INTO  projects_systems.public.\"User\" (name, employee_id, credentials, data) VALUES (?, ?, '-?-', 'No data')"
        );
        userInsert.setString(1, DBHelper.generateName());
        userInsert.setInt(2, employeeId);
        userInsert.executeUpdate();

        var categoriesName = DBHelper.generateName();
        var categoriesInsert = connection.prepareStatement(
                "INSERT INTO  projects_systems.public.\"P_Categories\" (name, description) VALUES (?, ?)"
        );
        categoriesInsert.setString(1, categoriesName);
        categoriesInsert.setString(2, DBHelper.generateDescription());
        categoriesInsert.executeUpdate();
        var categoriesId = dbHelper.selectCategoriesId(categoriesName);

        var categoriesProjectsInsert = connection.prepareStatement(
                "INSERT INTO  projects_systems.public.\"P_Categories_Projects\" (project_id, p_category_id) VALUES (?, ?)"
        );
        categoriesProjectsInsert.setInt(1, projectId);
        categoriesProjectsInsert.setInt(2, categoriesId);
        categoriesProjectsInsert.executeUpdate();

        var repositoryInsert = connection.prepareStatement(
                "INSERT INTO  projects_systems.public.\"Repository\" (link, description) VALUES ('-NO LINK-', ?)"
        );
        repositoryInsert.setString(1, DBHelper.generateDescription());
        var repositoryId = dbHelper.selectFirstRepositoryId();

        if(repositoryId == -1) {
            return;
        }

        var repositoryProjectsInsert = connection.prepareStatement(
                "INSERT INTO  projects_systems.public.\"Repository_Projects\" (project_id, repository_id) VALUES (?, ?)"
        );
        repositoryProjectsInsert.setInt(1, projectId);
        repositoryProjectsInsert.setInt(2, repositoryId);
        repositoryProjectsInsert.executeUpdate();

        var ruleName = DBHelper.generateName();
        var ruleInsert = connection.prepareStatement(
                "INSERT INTO  projects_systems.public.\"Rule\" (name, description) VALUES (?, ?)"
        );
        ruleInsert.setString(1, ruleName);
        ruleInsert.setString(2, DBHelper.generateDescription());
        ruleInsert.executeUpdate();
        var ruleId = dbHelper.selectRuleId(ruleName);

        var ruleProjectsInsert = connection.prepareStatement(
                "INSERT INTO  projects_systems.public.\"Rule_Projects\" (rule_id, project_id) VALUES (?, ?)"
        );
        ruleProjectsInsert.setInt(1, ruleId);
        ruleProjectsInsert.setInt(2, projectId);
        ruleProjectsInsert.executeUpdate();


        var roleName = DBHelper.generateName();
        var roleInsert = connection.prepareStatement(
                "INSERT INTO  projects_systems.public.\"Role\" (name, description) VALUES (?, ?)"
        );
        roleInsert.setString(1, roleName);
        roleInsert.setString(2, DBHelper.generateDescription());
        roleInsert.executeUpdate();
        var roleId = dbHelper.selectRoleId(roleName);

        var employeeTeamInsert = connection.prepareStatement(
                "INSERT INTO  projects_systems.public.\"Employee_Team\" (employee_id, team_id, role_id, start_date) VALUES (?, ?, ?, '2010/1/1')"
        );
        employeeTeamInsert.setInt(1, employeeId);
        employeeTeamInsert.setInt(2, teamId);
        employeeTeamInsert.setInt(3, roleId);
        employeeTeamInsert.executeUpdate();

        var skillName = DBHelper.generateName();
        var skillInsert = connection.prepareStatement(
                "INSERT INTO  projects_systems.public.\"Skill\" (name, description) VALUES (?, ?)"
        );
        skillInsert.setString(1, skillName);
        skillInsert.setString(2, DBHelper.generateDescription());
        skillInsert.executeUpdate();
        var skillId =  dbHelper.selectSkillId(skillName);

        var employeeSkillInsert = connection.prepareStatement(
                "INSERT INTO  projects_systems.public.\"Employee_Skill\" (employee_id, skill_id, level) VALUES (?, ?, ?)"
        );
        employeeSkillInsert.setInt(1, employeeId);
        employeeSkillInsert.setInt(2, skillId);
        employeeSkillInsert.setString(3, DBHelper.generateDescription());
        employeeSkillInsert.executeUpdate();

        var historyInsert = connection.prepareStatement(
                "INSERT INTO  projects_systems.public.\"E_History\" (employee_id, description, change_date) VALUES (?, ?, '2022.1.2')"
        );
        historyInsert.setInt(1, employeeId);
        historyInsert.setString(2, DBHelper.generateDescription());
        historyInsert.executeUpdate();
    }

    private static void printFirstQueryResult(ResultSet resultSet) {
        System.out.format("%38s\t%38s\t%38s\n", "Team Name", "Project Name", "T. Description");
        System.out.format(
                "%38s\t%38s\t%38s\n",
                "______________________________________",
                "______________________________________",
                "______________________________________"
        );
        try {
            while (resultSet.next()) {
                System.out.format(
                        "%38s\t%38s\t%38s\n",
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3)
                );
                System.out.println();
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    private static void printSecondQueryResult(ResultSet resultSet) {
        System.out.format("%40s\t%10s\n", "Team Name", "Count of employees");
        System.out.format(
                "%40s\t%10s\n",
                "____________________________________________",
                "__________"
        );
        try {
            while (resultSet.next()) {
                System.out.format(
                        "%40s\t%10s\n",
                        resultSet.getString(1),
                        resultSet.getString(2)
                );
                System.out.println();
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    private static void printThirdQueryResult(ResultSet resultSet) {
        System.out.format("%10s\t%40s\t%40s\n", "ID", "User", "Parent Id");
        System.out.format(
                "%10s\t%40s\t%40s\n",
                "__________",
                "____________________________________________",
                "____________________________________________"
        );
        try {
            while (resultSet.next()) {
                System.out.format(
                        "%10s\t%40s\t%40s\n",
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getInt(3)
                );
                System.out.println();
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }
}
