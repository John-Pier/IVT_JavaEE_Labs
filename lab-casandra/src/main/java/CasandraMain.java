import com.datastax.oss.driver.api.core.*;
import com.datastax.oss.driver.api.core.cql.*;
import com.datastax.oss.driver.api.core.data.UdtValue;
import com.datastax.oss.driver.api.core.type.UserDefinedType;
import com.datastax.oss.driver.api.querybuilder.QueryBuilder;
import com.datastax.oss.driver.api.querybuilder.insert.InsertInto;
import com.datastax.oss.driver.shaded.guava.common.collect.Maps;
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
//            insertValues();
            System.out.println("Update values");
            updateEntities();
            System.out.println("Print values");
//            printEntities();
        }
    }

    public static void insertValues() {
        var projectUuid = UUID.randomUUID();

        var projectsIds = new ArrayList<UUID>();
        projectsIds.add(projectUuid);

        insertProject(projectUuid);
        insertRepository(projectsIds);
        insertTeam(projectsIds);

        var employeeUuid = UUID.randomUUID();
        insertEmployee(employeeUuid);
        insertUser(employeeUuid);
    }

    private static void insertProject(UUID projectUuid) {
        var projectName = "Product " + DBHelper.generateId();
        var rulesMap = generateRulesMap();
        var projectQuery = session.execute("""
                INSERT INTO projects (id, description, last_update_timestamp, riles_map)
                VALUES ((?), (?), toTimeStamp(now()), (?));""", projectUuid, projectName, rulesMap);

    }

    private static void insertRepository(List<UUID> projectsIds) {
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
    }

    private static void insertTeam(List<UUID> projectsIds) {
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
    }

    private static void insertEmployee(UUID employeeUuid) {
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
    }

    private static void insertUser(UUID employeeUuid) {
        var userUuid = UUID.randomUUID();
        var userQuery = session.execute("""
                        INSERT INTO user (id, name, employee_id, credentials, data)
                        VALUES ((?), (?), (?), (?), (?));""",
                userUuid,
                DBHelper.generateName(),
                employeeUuid,
                "null ? or not", "some data" + DBHelper.generateId()
        );
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

    public static void updateEntities() {
        var position = session.execute("select * from projects");
        var row = position.one();
        if (row != null) {
            System.out.println("Selected: " + row.getFormattedContents());
            var rules = row.getMap("riles_map", String.class, String.class);

            System.out.println("Old rules: " + rules);
            session.execute(
                    QueryBuilder.update(DBEntityName.Project)
                            .setColumn("description", QueryBuilder.literal("Updated descr " + DBHelper.generateId()))
                            .toString()

            );

            var newRules = new HashMap<String, String>();
            newRules.put(DBHelper.generateName(), "New record descr " + DBHelper.generateId());
            session.execute(
                    QueryBuilder.update(DBEntityName.Project)
                            .append("riles_map", QueryBuilder.literal(newRules))
                            .toString()

            );

        }
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
        ResultSet resultSet = session.execute("select * from projects");
        for (var row : resultSet) {
            System.out.println("-------------------------------------");
            System.out.println("Project: " + row.getUuid("id"));
            System.out.println("description: " + row.getString("description"));
            System.out.println("riles_map: " + row.getMap("riles_map", String.class, String.class));
        }
        System.out.println("-------------------------------------");
    }

    public static void printRepository() {
        System.out.println("Repository values");
        ResultSet resultSet = session.execute("select * from repository");
        for (var row : resultSet) {
            System.out.println("-------------------------------------");
            System.out.println("Repository: " + row.getUuid("id"));
            System.out.println("description: " + row.getString("description"));
            System.out.println("link: " + row.getString("link"));
            System.out.println("projects_ids: " + row.getList("projects_ids", UUID.class));
        }
        System.out.println("-------------------------------------");
    }

    public static void printTeam() {
        System.out.println("Team values");
        ResultSet resultSet = session.execute("select * from team");
        for (var row : resultSet) {
            System.out.println("-------------------------------------");
            System.out.println("Team: " + row.getUuid("id"));
            System.out.println("name: " + row.getString("name"));
            System.out.println("description: " + row.getString("description"));
            System.out.println("projects_ids: " + row.getList("projects_ids", UUID.class));
        }
        System.out.println("-------------------------------------");
    }

    public static void printUser() {
        System.out.println("User values");
        ResultSet resultSet = session.execute("select * from user");
        for (var row : resultSet) {
            System.out.println("-------------------------------------");
            System.out.println("Team: " + row.getUuid("id"));
            System.out.println("name: " + row.getString("name"));
            System.out.println("employee_id: " + row.getUuid("employee_id"));
            System.out.println("credentials: " + row.getString("credentials"));
            System.out.println("data: " + row.getString("data"));
        }
        System.out.println("-------------------------------------");
    }

    public static void printEmployee() {
        System.out.println("Employee values");
        ResultSet resultSet = session.execute("select * from employee");
        for (var row : resultSet) {
            System.out.println("-------------------------------------");
            System.out.println("Team: " + row.getUuid("id"));
            System.out.println("name: " + row.getString("name"));
            System.out.println("start_date: " + row.getLocalDate("start_date"));
            System.out.println("data: " + row.getString("data"));

            var position = session.execute("select position from employee where id = ?", row.getUuid("id"));
            System.out.println("position: " + position.one().getFormattedContents());
        }
        System.out.println("-------------------------------------");
    }
}
