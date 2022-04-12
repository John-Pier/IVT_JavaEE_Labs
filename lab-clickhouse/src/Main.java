import com.clickhouse.jdbc.ClickHouseDataSource;
import helpers.DBHelper;

import java.sql.*;
import java.util.Properties;

public class Main {
    public static DBHelper dbHelper;

    public static void main(String[] args) throws SQLException {
        if (args.length == 0) {
            return;
        }

        String url = "jdbc:ch://localhost/projects_systems";//args[0];
        Properties props = new Properties();
        props.setProperty("client_name", "Agent #1");
        props.setProperty("ssl", "false");

        ClickHouseDataSource dataSource = new ClickHouseDataSource(url, props);
        try (var connection = dataSource.getConnection("default", "")) {
            dbHelper = new DBHelper(connection);
            var statement = connection.createStatement();
            // insertOperations(connection);

            //Команды и проекты над которыми они работают
            //firstQuery(statement);

            //Число человек в командах
            //secondQuery(statement);


            //thirdQuery(statement);

            System.out.println(connection.isClosed());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void firstQuery(Statement statement) throws SQLException {
        var resultSet = statement.executeQuery(
                """
                        select T.name, P.description, T.description from projects_systems.team_projects TP
                            JOIN projects_systems.team T on T.id = TP.team_id
                            JOIN projects_systems.projects P on P.id = TP.project_id
                            order by P.description"""
        );
        printFirstQueryResult(resultSet);
    }

    private static void secondQuery(Statement statement) throws SQLException {
        var resultSet = statement.executeQuery(
                """
                        select T.name as name, count(E.employee_id) eC from projects_systems.team T
                        LEFT OUTER JOIN projects_systems.employee_team E on E.team_id = T.id
                        group by (T.id, name)
                        having count(E.employee_id) > 0
                        ORDER BY eC DESC"""
        );
        printSecondQueryResult(resultSet);
    }

    private static void thirdQuery(Statement statement) throws SQLException {
        var resultSet = statement.executeQuery(
                """
                       WITH R AS (
                            SELECT U.id, U.name, U.parent_id
                            from projects_systems.user U
                            where U.id = 'e7be94ce-fef0-4f1a-8074-af984cf5e910'
                            UNION all
                            SELECT U.id, U.name, U.parent_id
                            from projects_systems.user U
                            JOIN projects_systems.user U1 on U1.id = U.parent_id
                       )
                       SELECT * FROM R;"""
        );
        printThirdQueryResult(resultSet);
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

    private static void insertOperations(Connection connection) throws SQLException {
        var projectName = "Product " + Math.exp(Math.random());
        var projectsInsert = connection.prepareStatement(
                "INSERT INTO  projects_systems.projects (description) VALUES (?)"
        );
        projectsInsert.setString(1, projectName);
        projectsInsert.executeUpdate();
        var projectId = dbHelper.selectFirstProjectId();

        if (projectId == null) {
            System.out.println("Error: projectId == null");
            return;
        }

        var teamInsert = connection.prepareStatement(
                "INSERT INTO  projects_systems.team (name, description) VALUES (?, ?)"
        );
        var name = DBHelper.generateName();
        teamInsert.setString(1, name);
        teamInsert.setString(2, DBHelper.generateDescription());
        teamInsert.executeUpdate();
        var teamId = dbHelper.selectTeamId(name);

        var teamProjectsInsert = connection.prepareStatement(
                "INSERT INTO  projects_systems.team_projects (project_id, team_id) VALUES (?, ?)"
        );
        teamProjectsInsert.setString(1, projectId);
        teamProjectsInsert.setString(2, teamId);
        teamProjectsInsert.executeUpdate();

        var positionName = DBHelper.generateName();
        var positionInsert = connection.prepareStatement(
                "INSERT INTO  projects_systems.position (name, description) VALUES (?, ?)"
        );
        positionInsert.setString(1, positionName);
        positionInsert.setString(2, DBHelper.generateDescription());
        positionInsert.executeUpdate();
        var positionId = dbHelper.selectPositionId(positionName);

        if (positionId == null) {
            System.out.println("Error: positionId == null");
            return;
        }

        var employeeName = DBHelper.generateName();
        var employeeInsert = connection.prepareStatement(
                "INSERT INTO  projects_systems.employee (name, start_date, position_id, data) VALUES (?, '2001.12.11', ?, 'No data')"
        );
        employeeInsert.setString(1, employeeName);
        employeeInsert.setString(2, positionId);
        employeeInsert.executeUpdate();
        var employeeId = dbHelper.selectEmployeeId(employeeName);

        var userInsert = connection.prepareStatement(
                "INSERT INTO  projects_systems.user (name, employee_id, credentials, data) VALUES (?, ?, '-?-', 'No data')"
        );
        userInsert.setString(1, DBHelper.generateName());
        userInsert.setString(2, employeeId);
        userInsert.executeUpdate();

        var categoriesName = DBHelper.generateName();
        var categoriesInsert = connection.prepareStatement(
                "INSERT INTO  projects_systems.p_categories (name, description) VALUES (?, ?)"
        );
        categoriesInsert.setString(1, categoriesName);
        categoriesInsert.setString(2, DBHelper.generateDescription());
        categoriesInsert.executeUpdate();
        var categoriesId = dbHelper.selectCategoriesId(categoriesName);

        var categoriesProjectsInsert = connection.prepareStatement(
                "INSERT INTO  projects_systems.p_categories_projects (project_id, p_category_id) VALUES (?, ?)"
        );
        categoriesProjectsInsert.setString(1, projectId);
        categoriesProjectsInsert.setString(2, categoriesId);
        categoriesProjectsInsert.executeUpdate();

        var repositoryInsert = connection.prepareStatement(
                "INSERT INTO  projects_systems.repository (link, description) VALUES ('-NO LINK-', ?)"
        );
        repositoryInsert.setString(1, DBHelper.generateDescription());
        repositoryInsert.executeUpdate();
        var repositoryId = dbHelper.selectFirstRepositoryId();

        if (repositoryId == null) {
            System.out.println("Error: repositoryId == null");
            return;
        }

        var repositoryProjectsInsert = connection.prepareStatement(
                "INSERT INTO  projects_systems.repository_projects (project_id, repository_id) VALUES (?, ?)"
        );
        repositoryProjectsInsert.setString(1, projectId);
        repositoryProjectsInsert.setString(2, repositoryId);
        repositoryProjectsInsert.executeUpdate();

        var ruleName = DBHelper.generateName();
        var ruleInsert = connection.prepareStatement(
                "INSERT INTO  projects_systems.rule (name, description) VALUES (?, ?)"
        );
        ruleInsert.setString(1, ruleName);
        ruleInsert.setString(2, DBHelper.generateDescription());
        ruleInsert.executeUpdate();
        var ruleId = dbHelper.selectRuleId(ruleName);

        var ruleProjectsInsert = connection.prepareStatement(
                "INSERT INTO  projects_systems.rule_projects (rule_id, project_id) VALUES (?, ?)"
        );
        ruleProjectsInsert.setString(1, ruleId);
        ruleProjectsInsert.setString(2, projectId);
        ruleProjectsInsert.executeUpdate();


        var roleName = DBHelper.generateName();
        var roleInsert = connection.prepareStatement(
                "INSERT INTO  projects_systems.role (name, description) VALUES (?, ?)"
        );
        roleInsert.setString(1, roleName);
        roleInsert.setString(2, DBHelper.generateDescription());
        roleInsert.executeUpdate();
        var roleId = dbHelper.selectRoleId(roleName);

        var employeeTeamInsert = connection.prepareStatement(
                "INSERT INTO  projects_systems.employee_team (employee_id, team_id, role_id, start_date) VALUES (?, ?, ?, '2010/1/1')"
        );
        employeeTeamInsert.setString(1, employeeId);
        employeeTeamInsert.setString(2, teamId);
        employeeTeamInsert.setString(3, roleId);
        employeeTeamInsert.executeUpdate();

        var skillName = DBHelper.generateName();
        var skillInsert = connection.prepareStatement(
                "INSERT INTO  projects_systems.skill (name, description) VALUES (?, ?)"
        );
        skillInsert.setString(1, skillName);
        skillInsert.setString(2, DBHelper.generateDescription());
        skillInsert.executeUpdate();
        var skillId = dbHelper.selectSkillId(skillName);

        var employeeSkillInsert = connection.prepareStatement(
                "INSERT INTO  projects_systems.employee_skill (employee_id, skill_id, level) VALUES (?, ?, ?)"
        );
        employeeSkillInsert.setString(1, employeeId);
        employeeSkillInsert.setString(2, skillId);
        employeeSkillInsert.setString(3, DBHelper.generateDescription());
        employeeSkillInsert.executeUpdate();

        var historyInsert = connection.prepareStatement(
                "INSERT INTO  projects_systems.e_History (employee_id, description, change_date) VALUES (?, ?, '2022.1.2')"
        );
        historyInsert.setString(1, employeeId);
        historyInsert.setString(2, DBHelper.generateDescription());
        historyInsert.executeUpdate();
    }
}
