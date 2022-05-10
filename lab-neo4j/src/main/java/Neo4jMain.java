import helpers.DBHelper;
import org.neo4j.driver.*;
import org.neo4j.driver.Driver;

import java.sql.SQLException;
import java.util.Random;
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
            //извлечь, показать
            insertOperations(session);
        }
    }

    private static void selectProjectsWithTeams(Result result) {

    }

    private static void printProjects(Result result) {

    }

    private static void insertOperations(Session session) throws SQLException {
        Random random = new Random();
        var projectId = random.nextInt(90000000);
        var repositoryId = random.nextInt(90000000);
        var categoryId = random.nextInt(90000000);
        var ruleId = random.nextInt(90000000);
        var teamId = random.nextInt(90000000);
        var projectName = "Product " + Math.exp(Math.random());

        session.writeTransaction(transaction -> {
            transaction.run(
                    "create (p:Project {description: $description, projectId: $projectId}) " +
                            "create (r:Repository {description: $description4, url: $url, id: $repositoryId}) " +
                            "create (c:Category {description: $description1, name: $name, id: $categoryId}) " +
                            "create (t:Team {description: $description2, name: $name1, id: $teamId}) " +
                            "create (ru:Rule {description: $description3, name: $name2, id: $ruleId}) ",
                    Values.parameters(
                            "description", projectName,
                            "projectId", projectId,
                            "ruleId", ruleId,
                            "repositoryId", repositoryId,
                            "categoryId", categoryId,
                            "teamId", teamId,
                            "url", "test-1",
                            "description1", DBHelper.generateDescription(),
                            "description2", DBHelper.generateDescription(),
                            "description3", DBHelper.generateDescription(),
                            "description4", DBHelper.generateDescription(),
                            "name", DBHelper.generateName(),
                            "name1", DBHelper.generateName(),
                            "name2", DBHelper.generateName()
                    )
            );
            transaction.run(
                    """
                            MATCH (p:Project) where p.projectId = $projectId\s
                            MATCH (r:Repository) where r.id = $repositoryId\s
                            MERGE (p)-[pr:PROJECT_HAVE_REPO]-(r)\s
                            \s""",
                    Values.parameters(
                            "projectId", projectId,
                            "repositoryId", repositoryId
                    )
            );
            transaction.run(
                    """
                            MATCH (p:Project) where p.projectId = $projectId\s
                            MATCH (c:Category) where c.id = $categoryId\s
                            MERGE (p)-[pr:PROJECT_HAVE_CATEGORY]-(c)\s
                            \s""",
                    Values.parameters(
                            "projectId", projectId,
                            "categoryId", categoryId
                    )
            );
            transaction.run(
                    """
                            MATCH (p:Project) where p.projectId = $projectId\s
                            MATCH (t:Team) where t.id = $teamId\s
                            MERGE (p)-[pr:PROJECT_HAVE_TEAM]-(t)\s
                            \s""",
                    Values.parameters(
                            "projectId", projectId,
                            "teamId", teamId
                    )
            );
            transaction.run(
                    """
                            MATCH (p:Project) where p.projectId = $projectId\s
                            MATCH (ru:Rule) where ru.id = $ruleId\s
                            MERGE (p)-[pr:PROJECT_HAVE_RULE]-(ru)\s
                            \s""",
                    Values.parameters(
                            "projectId", projectId,
                            "ruleId", ruleId
                    )
            );
            return null;
        });
    }
}
