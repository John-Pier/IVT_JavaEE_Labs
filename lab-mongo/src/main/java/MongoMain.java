import com.mongodb.client.*;
import com.mongodb.client.result.InsertOneResult;
import helpers.*;
import org.bson.Document;

import java.sql.ResultSet;
import java.util.*;


/***
 * docker run -p 27017:27017 --name mongo-inst -d mongo
 *
 **/
public class MongoMain {
    static MongoDatabase database;

    public static void main(String[] args) {
        String uri = "mongodb://localhost:27017";
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            database = mongoClient.getDatabase("projects_systems");

            System.out.println("Insert values");
            insertValues();
            System.out.println("Update values");
            updateEntities();
            System.out.println("Print values");
            printEntities();
        }
    }

    public static void insertValues() {
        var projectUuid = UUID.randomUUID();

        var projectsIds = new ArrayList<String>();
        projectsIds.add(projectUuid.toString());

        insertProject(projectUuid);
        insertRepository(projectsIds);
        insertTeam(projectsIds);

        var employeeUuid = UUID.randomUUID();
        insertEmployee(employeeUuid);
        insertUser(employeeUuid);
    }

    /**
     * projects_systems.projects id name description, rules obj( name, description),
     */
    private static void insertProject(UUID projectUuid) {
        var projectName = "Product " + DBHelper.generateId();
        var rules = generateRules();

        var projectDocument = new Document();
        projectDocument.append("id", projectUuid.toString());
        projectDocument.append("name", projectName);
        projectDocument.append("description", DBHelper.generateDescription());
        projectDocument.append("rules", rules);

        var collection = database.getCollection(DBEntityName.Projects);
       collection.insertOne(projectDocument);
    }

    private static List<Document> generateRules() {
        var result = new ArrayList<Document>(2);
        result.add(new Document().append("name", DBHelper.generateName()).append("description", DBHelper.generateDescription()));
        result.add(new Document().append("name", DBHelper.generateName()).append("description", DBHelper.generateDescription()));
        return result;
    }

    /**
     * projects_systems.repositories id link description projectsIds (LIST<UUID>)
     */
    private static void insertRepository(List<String> projectsIds) {
        var repositoryUuid = UUID.randomUUID();
        var repositoryDocument = new Document();
        repositoryDocument.append("id", repositoryUuid.toString());
        repositoryDocument.append("link", "http:/some-addr.ru/prj/" + DBHelper.generateId());
        repositoryDocument.append("description",  DBHelper.generateDescription());
        repositoryDocument.append("projectsIds", projectsIds);

        var collection = database.getCollection(DBEntityName.Repositories);
        collection.insertOne(repositoryDocument);
    }

    /**
     * projects_systems.teams id name description projectsIds(LIST<UUID>)
     */
    private static void insertTeam(List<String> projectsIds) {
        var teamUuid = UUID.randomUUID();
        var teamDocument = new Document();
        teamDocument.append("id", teamUuid.toString());
        teamDocument.append("name",DBHelper.generateName());
        teamDocument.append("description",  DBHelper.generateDescription());
        teamDocument.append("projectsIds", projectsIds);

        var collection = database.getCollection(DBEntityName.Teams);
        collection.insertOne(teamDocument);
    }

    /**
     * projects_systems.employees id name start_date position{name, description} data{}
     */
    private static void insertEmployee(UUID employeeUuid) {
        var employeeDocument = new Document();
        employeeDocument.append("id", employeeUuid.toString());
        employeeDocument.append("name",DBHelper.generateName());
        employeeDocument.append("start_date", DBHelper.generateDescription());
        employeeDocument.append("position", generatePosition());
        employeeDocument.append("data", new Document());

        var collection = database.getCollection(DBEntityName.Employees);
        collection.insertOne(employeeDocument);
    }

    private static Document generatePosition() {
        return new Document()
                .append("id", DBHelper.generateId())
                .append("name", DBHelper.generateName())
                .append("description", DBHelper.generateDescription());
    }

    /**
     * projects_systems.users id name employee_id credentials data{}
     */
    private static void insertUser(UUID employeeUuid) {
        var userUuid = UUID.randomUUID();
        var usersDocument = new Document();
        usersDocument.append("id", userUuid.toString());
        usersDocument.append("name",DBHelper.generateName());
        usersDocument.append("employee_id",employeeUuid.toString());
        usersDocument.append("credentials", null);
        usersDocument.append("data", new Document());

        var collection = database.getCollection(DBEntityName.Users);
        collection.insertOne(usersDocument);
    }

    public static void updateEntities() {
//        var resultSet = session.execute("select * from projects");
//        var row = resultSet.one();
//        if (row == null) {
//            return;
//        }
//
//        System.out.println("Selected: " + row.getFormattedContents());
//
//        UUID selectedUuid = row.getUuid("id");
//        var rules = row.getMap("riles_map", String.class, String.class);
//        var newRules = new HashMap<String, String>();
//        newRules.put(DBHelper.generateName(), "New record descr " + DBHelper.generateId());
//
//        System.out.println("Old rules: " + rules);
//
//        session.execute(
//                QueryBuilder.update(DBEntityName.Project)
//                        .setColumn("description", QueryBuilder.literal("Updated descr " + DBHelper.generateId()))
//                        .append("riles_map", QueryBuilder.literal(newRules))
//                        .whereColumn("id").isEqualTo(QueryBuilder.literal(selectedUuid))
//                        .build()
//
//        );
    }

    public static void printEntities() {
//        MongoCollection<Document> collection = database.getCollection(DBEntityName.Projects);
//        //Filters.eq("title", "Back to the Future")
//        Document doc = collection.find().first();
//        System.out.println(database.listCollectionNames().first());
//        System.out.println(doc.toJson());

        printProject();
        printRepository();
        printTeam();
        printUser();
        printEmployee();
    }

    public static void printProject() {
        System.out.println("Project values");

        MongoCollection<Document> collection = database.getCollection(DBEntityName.Projects);
        var documents = collection.find();
        for (var document : documents) {
            System.out.println("-------------------------------------");
            System.out.println("Project: " + document.getString("id"));
            System.out.println("_id: " + document.getObjectId("_id"));
            System.out.println("name: " + document.getString("name"));
            System.out.println("description: " + document.getString("description"));
            System.out.println("rules: " + document.get("rules", Document.class).toJson());
        }
        System.out.println("-------------------------------------");
    }

    public static void printRepository() {
        System.out.println("Repository values");
//        ResultSet resultSet = session.execute("select * from repository");
//        for (var row : resultSet) {
//            System.out.println("-------------------------------------");
//            System.out.println("Repository: " + row.getUuid("id"));
//            System.out.println("description: " + row.getString("description"));
//            System.out.println("link: " + row.getString("link"));
//            System.out.println("projects_ids: " + row.getList("projects_ids", UUID.class));
//        }
        System.out.println("-------------------------------------");
    }

    public static void printTeam() {
        System.out.println("Team values");
//        ResultSet resultSet = session.execute("select * from team");
//        for (var row : resultSet) {
//            System.out.println("-------------------------------------");
//            System.out.println("Team: " + row.getUuid("id"));
//            System.out.println("name: " + row.getString("name"));
//            System.out.println("description: " + row.getString("description"));
//            System.out.println("projects_ids: " + row.getList("projects_ids", UUID.class));
//        }
        System.out.println("-------------------------------------");
    }

    public static void printUser() {
        System.out.println("User values");
//        ResultSet resultSet = session.execute("select * from user");
//        for (var row : resultSet) {
//            System.out.println("-------------------------------------");
//            System.out.println("Team: " + row.getUuid("id"));
//            System.out.println("name: " + row.getString("name"));
//            System.out.println("employee_id: " + row.getUuid("employee_id"));
//            System.out.println("credentials: " + row.getString("credentials"));
//            System.out.println("data: " + row.getString("data"));
//        }
        System.out.println("-------------------------------------");
    }

    public static void printEmployee() {
        System.out.println("Employee values");
//        ResultSet resultSet = session.execute("select * from employee");
//        for (var row : resultSet) {
//            System.out.println("-------------------------------------");
//            System.out.println("Team: " + row.getUuid("id"));
//            System.out.println("name: " + row.getString("name"));
//            System.out.println("start_date: " + row.getLocalDate("start_date"));
//            System.out.println("data: " + row.getString("data"));
//
//            var position = session.execute("select position from employee where id = ?", row.getUuid("id"));
//            System.out.println("position: " + position.one().getFormattedContents());
//        }
        System.out.println("-------------------------------------");
    }
}


