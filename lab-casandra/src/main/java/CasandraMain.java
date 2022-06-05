import com.datastax.oss.driver.api.core.*;
import com.datastax.oss.driver.api.core.cql.*;
import com.datastax.oss.driver.api.core.data.UdtValue;
import com.datastax.oss.driver.api.core.type.UserDefinedType;
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

        var projectsIds = new ArrayList<UUID>();
        projectsIds.add(projectUuid);

        var rulesMap = generateRulesMap();

        var projectQuery = session.execute("""
                INSERT INTO projects (id, description, last_update_timestamp, riles_map)
                VALUES ((?), (?), toTimeStamp(now()), (?));""", projectUuid, projectName, rulesMap);

        var repositoryUuid = UUID.randomUUID();
        var repositoryQuery = session.prepare("""
                INSERT INTO repository (id, description, link, projects_ids)
                VALUES (:id, :description, :link, :projects);""");
        session.execute(
                repositoryQuery.boundStatementBuilder()
                        .setUuid("id", repositoryUuid)
                        .setString("description", DBHelper.generateDescription())
                        .setString("link", "http:/some-addr.ru/prj/" + DBHelper.generateId())
                        .setList("projects", projectsIds, UUID.class)
                        .build()
        );

        var teamUuid = UUID.randomUUID();
        var teamQuery = session.prepare("""
                INSERT INTO team (id, name, description, projects_ids)
                VALUES (:id, :name, :description, :projects)""");
        session.execute(
                teamQuery.boundStatementBuilder()
                        .setUuid("id", teamUuid)
                        .setString("description", DBHelper.generateDescription())
                        .setString("name", DBHelper.generateName())
                        .setList("projects", projectsIds, UUID.class)
                        .build()
        );

        var employeeUuid = UUID.randomUUID();
        var employeeQuery = session.prepare("""
                INSERT INTO employee (id, name, start_date, position, data)
                VALUES (:id, :name, toDate(now()), :position, :data);""");

        UserDefinedType positionType = (UserDefinedType) employeeQuery.getVariableDefinitions().get("position").getType();

        session.execute(
                employeeQuery.boundStatementBuilder()
                        .setUuid("id", employeeUuid)
                        .setString("name", DBHelper.generateName())
                        .setString("data", "some data" + DBHelper.generateId())
                        .setUdtValue("position", generatePosition(positionType))
                        .build()
        );

        var userUuid = UUID.randomUUID();
        var userQuery = session.execute("""
                INSERT INTO user (id, name, employee_id, credentials, data)
                VALUES ((?), (?), (?), (?), (?));""", userUuid, DBHelper.generateName(), employeeUuid, "null ? or not", "some data" + DBHelper.generateId());
    }

    private static UdtValue generatePosition(UserDefinedType positionType) {
        return positionType.newValue()
                .setString(0, DBHelper.generateName())
                .setString(1, DBHelper.generateDescription());
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
