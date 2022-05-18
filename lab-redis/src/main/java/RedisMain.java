import helpers.*;
import org.json.JSONObject;
import redis.clients.jedis.*;

import java.util.Random;

/***
 * To run Docker image:  docker run -p 6379:6379 -d redis
 */

public class RedisMain {

    public static void main(String[] args) throws Exception {
        JedisPool pool = new JedisPool("localhost", 6379);
        try (var jedis = pool.getResource()) {
            System.out.println("isConnected: " + jedis.isConnected());

            System.out.println("Projects: ");
            var projects = jedis.smembers(DBEntityName.Project);
            System.out.println(projects);

            System.out.println("Teams: ");
            var teams = jedis.smembers(DBEntityName.Team);
            System.out.println(teams);

            System.out.println("Repositories: ");
            var repositories = jedis.smembers(DBEntityName.Repository);
            System.out.println(repositories);

            System.out.println("Rules: ");
            var rules = jedis.smembers(DBEntityName.Rule);
            System.out.println(rules);

            System.out.println("Categories: ");
            var categories = jedis.smembers(DBEntityName.Category);
            System.out.println(categories);

            System.out.println("Insert values..: ");
            insertValues(jedis);

            System.out.println("Print values..: ");
            printEntities(jedis, updateEntities(jedis));
        }
        pool.close();
    }

    public static void insertValues(Jedis jedis) {
        // Варианты: хэши, джесоны(строки), сеты как таблицы
        Random random = new Random();
        var projectId = DBEntityName.Project + ":" + random.nextInt(90000000);
        var repositoryId = DBEntityName.Repository + ":" + random.nextInt(90000000);
        var categoryId = DBEntityName.Category + ":" + random.nextInt(90000000);
        var ruleId = DBEntityName.Rule + ":" + random.nextInt(90000000);
        var teamId = DBEntityName.Team + ":" + random.nextInt(90000000);

        jedis.set(projectId, generateProject(projectId).toString());
        jedis.set(repositoryId, generateRepository(repositoryId).toString());
        jedis.set(categoryId, generateCategory(categoryId).toString());
        jedis.set(teamId, generateTeam(teamId).toString());
        jedis.set(ruleId, generateRule(ruleId).toString());

        jedis.sadd(DBEntityName.Project, projectId);
        jedis.sadd(DBEntityName.Repository, repositoryId);
        jedis.sadd(DBEntityName.Category, categoryId);
        jedis.sadd(DBEntityName.Rule, ruleId);
        jedis.sadd(DBEntityName.Team, teamId);
    }

    private static JSONObject generateProject(String projectId) {
        var projectName = "Product " + Math.exp(Math.random());
        JSONObject obj = new JSONObject();
        obj.put("description", projectName);
        obj.put("projectId", projectId);
        return obj;
    }

    private static JSONObject generateRepository(String repositoryId) {
        JSONObject obj = new JSONObject();
        obj.put("description", DBHelper.generateDescription());
        obj.put("url", "-");
        obj.put("id", repositoryId);
        return obj;
    }

    private static JSONObject generateCategory(String categoryId) {
        return generateObject(categoryId);
    }

    private static JSONObject generateTeam(String teamId) {
        return generateObject(teamId);
    }

    private static JSONObject generateRule(String ruleId) {
        return generateObject(ruleId);
    }

    private static JSONObject generateObject(String id) {
        JSONObject obj = new JSONObject();
        obj.put("description", DBHelper.generateDescription());
        obj.put("name", DBHelper.generateName());
        obj.put("id", id);
        return obj;
    }

    public static String updateEntities(Jedis jedis) {
        var randomId = jedis.spop(DBEntityName.Project);
        printEntities(jedis, randomId);

        jedis.set(randomId, generateProject(randomId).toString());
        return randomId;
    }

    public static void printEntities(Jedis jedis, String id) {
        System.out.println("Entity: " + id);
        var entity = jedis.get(id);
        System.out.println(entity);
    }
}
