import com.datastax.oss.driver.api.core.*;
import com.datastax.oss.driver.api.core.cql.*;
import com.datastax.oss.driver.api.querybuilder.QueryBuilder;
import com.datastax.oss.driver.api.querybuilder.insert.InsertInto;
import helpers.*;

import java.net.InetSocketAddress;
import java.util.*;

/***
 * To run Docker image:
 *  docker run --name casandra-inst  -p 9042:9042 -p 7000:7000 -p 9160:9160 -p 7199:7199 -d cassandra
 * login -> cassandra
 * to set password -> -e CASSANDRA_PASSWORD=cassandra
 */

public class CasandraMain {

    private static CqlSession session;

    public static void main(String[] args) throws Exception {
        CqlSession session = CqlSession.builder()
                .addContactPoint(new InetSocketAddress("localhost", 9042))
                .withLocalDatacenter("datacenter1")
                .withKeyspace(CqlIdentifier.fromCql("projects_systems"))
                .build();

        try (session) {
            CasandraMain.session = session;
            ResultSet rs = session.execute("select release_version from system.local");
            Row row = rs.one();
            System.out.println("release_version: " + row.getString("release_version"));
            System.out.println("isClosed: " + session.isClosed());

            System.out.println("Insert values");
            insertValues();
            System.out.println("Update values");
            updateEntities();
            System.out.println("Print values");
            printEntities();
        }
    }

    public static void insertValues() {
        var projectName = "Product " + DBHelper.generateId();
        var projectUuid = UUID.randomUUID();
        var projectsIds = new String[]{String.valueOf(projectUuid)};
        var rulesMap = generateRulesMap();

        var projectQuery = session.execute("""
                INSERT INTO projects
                (id, description, last_update_timestamp, riles_map)
                VALUES ((?), (?), toTimeStamp(now()), (?));""", projectUuid, projectName, rulesMap);

//        var repositoryUuid = UUID.randomUUID();
//        var repositoryInsert = QueryBuilder.insertInto("repository");
//        var statement = repositoryInsert
//                .value("id", QueryBuilder.bindMarker())
//                .value("description", QueryBuilder.bindMarker())
//                .value("link", QueryBuilder.bindMarker())
//                .value("projects_ids", QueryBuilder.bindMarker())
//                .build(repositoryUuid, DBHelper.generateDescription(), "http:/some-addr.ru/" + DBHelper.generateId(), "[" + projectUuid + "]");
//        session.execute(statement);

//        var repositoryQuery = session.execute("""
//                INSERT INTO repository
//                (id, description, link, projects_ids)
//                VALUES ((?), (?), (?), (?));""", UUID.randomUUID(), DBHelper.generateDescription(), "http:/some-addr.ru/" + DBHelper.generateId(), "["+ projectUuid + "]");

//        var teamUuid = UUID.randomUUID();
//        var teamQuery = session.execute("""
//                INSERT INTO team
//                (id, name, description, projects_ids)
//                VALUES ((?), (?), (?), (?));""", teamUuid, DBHelper.generateName(), DBHelper.generateDescription(), "["+ projectUuid + "]");

//        System.out.println(generatePosition());

        var employeeUuid = UUID.randomUUID();
        var employeeQuery = session.execute("""
                INSERT INTO employee
                (id, name, start_date, position, data)
                VALUES ((?), (?), toDate(now()), (?), (?));""", employeeUuid, DBHelper.generateName(), generatePosition(), "some data" + DBHelper.generateId());

        var userUuid = UUID.randomUUID();
        var userQuery = session.execute("""
                INSERT INTO user
                (id, name, employee_id, credentials, data)
                VALUES ((?), (?), (?), (?), (?));""", userUuid, DBHelper.generateName(), employeeUuid, "null ? or not", "some data" + DBHelper.generateId());
    }

    private static String generatePosition() {
        return String.format("{name: '%1$s', description: '%2$s' }", DBHelper.generateName(), DBHelper.generateDescription());
    }

    private static Map<String, String> generateRulesMap() {
        Map<String, String> rulesMap = new HashMap<>();
        rulesMap.put(DBHelper.generateName(), DBHelper.generateDescription());
        rulesMap.put(DBHelper.generateName(), DBHelper.generateDescription());
        if (Math.random() > 0.5) {
            rulesMap.put(DBHelper.generateName(), DBHelper.generateDescription());
        }
        return rulesMap;
    }

    public static String updateEntities() {
        return null;
    }

    public static void printEntities() {

    }
}
