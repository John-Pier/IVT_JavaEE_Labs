import com.mongodb.MongoException;
import com.mongodb.client.*;
import com.mongodb.client.model.*;
import com.mongodb.client.result.*;
import helpers.*;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.sql.ResultSet;
import java.util.*;


/***
 * run mongoDB:
 * docker run -p 27017:27017 --name mongo-inst -d mongo
 **/
public class MongoMain {
    static MongoDatabase database;

    public static void main(String[] args) {
        String uri = "mongodb://localhost:27017";
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            database = mongoClient.getDatabase("projects_systems");

            System.out.println("Insert values...");
            insertValues();
            System.out.println("Update values...");
            updateEntities();
            System.out.println("Print values...");
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
        result.add(generateRule());
        result.add(generateRule());
        return result;
    }

    private static Document generateRule() {
        return new Document()
                .append("name", DBHelper.generateName())
                .append("description", DBHelper.generateDescription());
    }

    /**
     * projects_systems.repositories id link description projectsIds (LIST<UUID>)
     */
    private static void insertRepository(List<String> projectsIds) {
        var repositoryUuid = UUID.randomUUID();
        var repositoryDocument = new Document();
        repositoryDocument.append("id", repositoryUuid.toString());
        repositoryDocument.append("link", "http:/some-addr.ru/prj/" + DBHelper.generateId());
        repositoryDocument.append("description", DBHelper.generateDescription());
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
        teamDocument.append("name", DBHelper.generateName());
        teamDocument.append("description", DBHelper.generateDescription());
        teamDocument.append("projectsIds", projectsIds);

        var collection = database.getCollection(DBEntityName.Teams);
        collection.insertOne(teamDocument);
    }

    /**
     * projects_systems.employees id name startDate position{name, description} data{}
     */
    private static void insertEmployee(UUID employeeUuid) {
        var employeeDocument = new Document();
        employeeDocument.append("id", employeeUuid.toString());
        employeeDocument.append("name", DBHelper.generateName());
        employeeDocument.append("startDate", new Date());
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
        usersDocument.append("name", DBHelper.generateName());
        usersDocument.append("employee_id", employeeUuid.toString());
        usersDocument.append("credentials", null);
        usersDocument.append("data", new Document());

        var collection = database.getCollection(DBEntityName.Users);
        collection.insertOne(usersDocument);
    }

    public static void updateEntities() {
        var collection = database.getCollection(DBEntityName.Projects);
        var sortQuery = new Document().append("name", 1);
        var firstProject = collection.find().sort(sortQuery).first();
        if (firstProject == null) {
            return;
        }
        var id = firstProject.getObjectId("_id");

        System.out.println("Selected: " + firstProject.toJson());

        Document query = new Document().append("_id", id);
        Bson updates = Updates.combine(
                Updates.set("name", "Updated Name: " + DBHelper.generateId()),
                Updates.set("description", "Updated description: " + DBHelper.generateId()),
                Updates.push("rules", generateRule()),
                Updates.currentTimestamp("lastUpdated"));

        var result = collection.updateOne(query, updates, new UpdateOptions().upsert(true));

        System.out.println("Modified project count: " + result.getModifiedCount());
        System.out.println("Updated id: " + result.getUpsertedId());
    }

    public static void printEntities() {
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
            System.out.println("rules: " + document.get("rules", ArrayList.class).toString());
        }
        System.out.println("-------------------------------------");
    }

    public static void printRepository() {
        System.out.println("Repository values");
        MongoCollection<Document> collection = database.getCollection(DBEntityName.Repositories);
        var documents = collection.find();
        for (var document : documents) {
            System.out.println("-------------------------------------");
            System.out.println("Repository: " + document.getString("id"));
            System.out.println("_id: " + document.getObjectId("_id"));
            System.out.println("link: " + document.getString("link"));
            System.out.println("description: " + document.getString("description"));
            System.out.println("projectsIds: " + document.get("projectsIds", ArrayList.class).toString());
        }
        System.out.println("-------------------------------------");
    }

    public static void printTeam() {
        System.out.println("Team values");
        MongoCollection<Document> collection = database.getCollection(DBEntityName.Teams);
        var documents = collection.find();
        for (var document : documents) {
            System.out.println("-------------------------------------");
            System.out.println("Team: " + document.getString("id"));
            System.out.println("_id: " + document.getObjectId("_id"));
            System.out.println("name: " + document.getString("name"));
            System.out.println("description: " + document.getString("description"));
            System.out.println("projectsIds: " + document.get("projectsIds", ArrayList.class).toString());
        }
        System.out.println("-------------------------------------");
    }

    public static void printUser() {
        System.out.println("User values");
        MongoCollection<Document> collection = database.getCollection(DBEntityName.Users);
        var documents = collection.find();
        for (var document : documents) {
            System.out.println("-------------------------------------");
            System.out.println("User: " + document.getString("id"));
            System.out.println("_id: " + document.getObjectId("_id"));
            System.out.println("name: " + document.getString("name"));
            System.out.println("employee_id: " + document.getString("employee_id"));
            System.out.println("credentials: " + document.get("credentials"));
            System.out.println("data: " + document.get("data", Document.class).toJson());
        }
        System.out.println("-------------------------------------");
    }

    public static void printEmployee() {
        System.out.println("Employee values");
        MongoCollection<Document> collection = database.getCollection(DBEntityName.Employees);
        var documents = collection.find();
        for (var document : documents) {
            System.out.println("-------------------------------------");
            System.out.println("Employee: " + document.getString("id"));
            System.out.println("_id: " + document.getObjectId("_id"));
            System.out.println("name: " + document.getString("name"));
            System.out.println("startDate: " + document.getDate("startDate"));
            System.out.println("position: " + document.get("position", Document.class).toJson());
            System.out.println("data: " + document.get("data", Document.class).toJson());
        }
        System.out.println("-------------------------------------");
    }
}


