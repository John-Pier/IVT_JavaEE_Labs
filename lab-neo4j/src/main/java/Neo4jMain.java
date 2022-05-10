import org.neo4j.driver.*;
import org.neo4j.driver.Driver;
import java.util.logging.Logger;

public class Neo4jMain {
    private static final Logger LOGGER = Logger.getLogger(Neo4jMain.class.getName());

    private static Driver driver;

    public static void main(String[] args) throws Exception {
        if(args.length < 2) {
            throw new Exception("Нет обязательных аргументов!");
        }
        driver = GraphDatabase.driver(args[0], AuthTokens.basic("neo4j", args[1]));

        try (Session session = driver.session())
        {
            System.out.println(session.isOpen());
//            String greeting = session.writeTransaction( tx ->
//            {
//                Result result = tx.run( "CREATE (a:Greeting) " +
//                                "SET a.message = $message " +
//                                "RETURN a.message + ', from node ' + id(a)",
//                        Values.parameters( "message", "message") );
//                return result.single().get( 0 ).asString();
//            } );
//            System.out.println( greeting );
        }
    }

//    private static void insertOperations(Connection connection) throws SQLException {
//        var projectName = "Product " + Math.exp(Math.random());
//        var projectsInsert = connection.prepareStatement(
//                "INSERT INTO  projects_systems.projects (description) VALUES (?)"
//        );
//        projectsInsert.setString(1, projectName);
//        projectsInsert.executeUpdate();
//        var projectId = dbHelper.selectFirstProjectId();
//
//        if (projectId == null) {
//            System.out.println("Error: projectId == null");
//            return;
//        }
//
//        var teamInsert = connection.prepareStatement(
//                "INSERT INTO  projects_systems.team (name, description) VALUES (?, ?)"
//        );
//        var name = DBHelper.generateName();
//        teamInsert.setString(1, name);
//        teamInsert.setString(2, DBHelper.generateDescription());
//        teamInsert.executeUpdate();
//        var teamId = dbHelper.selectTeamId(name);
//
//        var teamProjectsInsert = connection.prepareStatement(
//                "INSERT INTO  projects_systems.team_projects (project_id, team_id) VALUES (?, ?)"
//        );
//        teamProjectsInsert.setString(1, projectId);
//        teamProjectsInsert.setString(2, teamId);
//        teamProjectsInsert.executeUpdate();
//
//        var positionName = DBHelper.generateName();
//        var positionInsert = connection.prepareStatement(
//                "INSERT INTO  projects_systems.position (name, description) VALUES (?, ?)"
//        );
//        positionInsert.setString(1, positionName);
//        positionInsert.setString(2, DBHelper.generateDescription());
//        positionInsert.executeUpdate();
//        var positionId = dbHelper.selectPositionId(positionName);
//
//        if (positionId == null) {
//            System.out.println("Error: positionId == null");
//            return;
//        }
//
//        var employeeName = DBHelper.generateName();
//        var employeeInsert = connection.prepareStatement(
//                "INSERT INTO  projects_systems.employee (name, start_date, position_id, data) VALUES (?, '2001.12.11', ?, 'No data')"
//        );
//        employeeInsert.setString(1, employeeName);
//        employeeInsert.setString(2, positionId);
//        employeeInsert.executeUpdate();
//        var employeeId = dbHelper.selectEmployeeId(employeeName);
//
//        var userInsert = connection.prepareStatement(
//                "INSERT INTO  projects_systems.user (name, employee_id, credentials, data) VALUES (?, ?, '-?-', 'No data')"
//        );
//        userInsert.setString(1, DBHelper.generateName());
//        userInsert.setString(2, employeeId);
//        userInsert.executeUpdate();
//
//        var categoriesName = DBHelper.generateName();
//        var categoriesInsert = connection.prepareStatement(
//                "INSERT INTO  projects_systems.p_categories (name, description) VALUES (?, ?)"
//        );
//        categoriesInsert.setString(1, categoriesName);
//        categoriesInsert.setString(2, DBHelper.generateDescription());
//        categoriesInsert.executeUpdate();
//        var categoriesId = dbHelper.selectCategoriesId(categoriesName);
//
//        var categoriesProjectsInsert = connection.prepareStatement(
//                "INSERT INTO  projects_systems.p_categories_projects (project_id, p_category_id) VALUES (?, ?)"
//        );
//        categoriesProjectsInsert.setString(1, projectId);
//        categoriesProjectsInsert.setString(2, categoriesId);
//        categoriesProjectsInsert.executeUpdate();
//
//        var repositoryInsert = connection.prepareStatement(
//                "INSERT INTO  projects_systems.repository (link, description) VALUES ('-NO LINK-', ?)"
//        );
//        repositoryInsert.setString(1, DBHelper.generateDescription());
//        repositoryInsert.executeUpdate();
//        var repositoryId = dbHelper.selectFirstRepositoryId();
//
//        if (repositoryId == null) {
//            System.out.println("Error: repositoryId == null");
//            return;
//        }
//
//        var repositoryProjectsInsert = connection.prepareStatement(
//                "INSERT INTO  projects_systems.repository_projects (project_id, repository_id) VALUES (?, ?)"
//        );
//        repositoryProjectsInsert.setString(1, projectId);
//        repositoryProjectsInsert.setString(2, repositoryId);
//        repositoryProjectsInsert.executeUpdate();
//
//        var ruleName = DBHelper.generateName();
//        var ruleInsert = connection.prepareStatement(
//                "INSERT INTO  projects_systems.rule (name, description) VALUES (?, ?)"
//        );
//        ruleInsert.setString(1, ruleName);
//        ruleInsert.setString(2, DBHelper.generateDescription());
//        ruleInsert.executeUpdate();
//        var ruleId = dbHelper.selectRuleId(ruleName);
//
//        var ruleProjectsInsert = connection.prepareStatement(
//                "INSERT INTO  projects_systems.rule_projects (rule_id, project_id) VALUES (?, ?)"
//        );
//        ruleProjectsInsert.setString(1, ruleId);
//        ruleProjectsInsert.setString(2, projectId);
//        ruleProjectsInsert.executeUpdate();
//
//
//        var roleName = DBHelper.generateName();
//        var roleInsert = connection.prepareStatement(
//                "INSERT INTO  projects_systems.role (name, description) VALUES (?, ?)"
//        );
//        roleInsert.setString(1, roleName);
//        roleInsert.setString(2, DBHelper.generateDescription());
//        roleInsert.executeUpdate();
//        var roleId = dbHelper.selectRoleId(roleName);
//
//        var employeeTeamInsert = connection.prepareStatement(
//                "INSERT INTO  projects_systems.employee_team (employee_id, team_id, role_id, start_date) VALUES (?, ?, ?, '2010/1/1')"
//        );
//        employeeTeamInsert.setString(1, employeeId);
//        employeeTeamInsert.setString(2, teamId);
//        employeeTeamInsert.setString(3, roleId);
//        employeeTeamInsert.executeUpdate();
//
//        var skillName = DBHelper.generateName();
//        var skillInsert = connection.prepareStatement(
//                "INSERT INTO  projects_systems.skill (name, description) VALUES (?, ?)"
//        );
//        skillInsert.setString(1, skillName);
//        skillInsert.setString(2, DBHelper.generateDescription());
//        skillInsert.executeUpdate();
//        var skillId = dbHelper.selectSkillId(skillName);
//
//        var employeeSkillInsert = connection.prepareStatement(
//                "INSERT INTO  projects_systems.employee_skill (employee_id, skill_id, level) VALUES (?, ?, ?)"
//        );
//        employeeSkillInsert.setString(1, employeeId);
//        employeeSkillInsert.setString(2, skillId);
//        employeeSkillInsert.setString(3, DBHelper.generateDescription());
//        employeeSkillInsert.executeUpdate();
//
//        var historyInsert = connection.prepareStatement(
//                "INSERT INTO  projects_systems.e_History (employee_id, description, change_date) VALUES (?, ?, '2022.1.2')"
//        );
//        historyInsert.setString(1, employeeId);
//        historyInsert.setString(2, DBHelper.generateDescription());
//        historyInsert.executeUpdate();
//    }
}
