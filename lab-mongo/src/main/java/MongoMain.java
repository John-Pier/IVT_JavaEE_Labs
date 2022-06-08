import com.mongodb.client.*;
import helpers.*;
import org.bson.Document;

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

        var projectsIds = new ArrayList<UUID>();
        projectsIds.add(projectUuid);

//        insertProject(projectUuid);
//        insertRepository(projectsIds);
//        insertTeam(projectsIds);
//
//        var employeeUuid = UUID.randomUUID();
//        insertEmployee(employeeUuid);
//        insertUser(employeeUuid);
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
        MongoCollection<Document> collection = database.getCollection(DBEntityName.Projects);
        //Filters.eq("title", "Back to the Future")
        Document doc = collection.find().first();
        System.out.println(database.listCollectionNames().first());
        System.out.println(doc.toJson());

//        printProject();
//        printRepository();
//        printTeam();
//        printUser();
//        printEmployee();
    }
}


