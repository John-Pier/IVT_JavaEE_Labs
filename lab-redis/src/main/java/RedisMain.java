import helpers.DBHelper;
import org.json.JSONObject;
import redis.clients.jedis.*;

import java.io.StringWriter;
import java.util.Random;

/***
 * To run Docker image:  docker run -p 6379:6379 -d redis
 */

public class RedisMain {

    public static void main(String[] args) throws Exception {
        JedisPool pool = new JedisPool("localhost", 6379);
        try(var jedis = pool.getResource()) {
            System.out.println(jedis.isConnected());
            System.out.println(jedis.clientGetname());
            // insertValues(jedis);

        }
        pool.close();
    }

    public static void insertValues(Jedis jedis) {
        Random random = new Random();
        var projectId = "project:" + random.nextInt(90000000);
        var repositoryId = "repository:" + random.nextInt(90000000);
        var categoryId = "category:" + random.nextInt(90000000);
        var ruleId = "rule:" + random.nextInt(90000000);
        var teamId = "team:" + random.nextInt(90000000);

        jedis.set(projectId,  generateProject(projectId).toString());
        jedis.set(repositoryId,  generateRepository(repositoryId).toString());
        jedis.set(categoryId,  generateCategory(categoryId).toString());
        jedis.set(teamId,  generateTeam(teamId).toString());
        jedis.set(ruleId,  generateRule(ruleId).toString());

    }

    private static JSONObject generateProject(String projectId) {
        var projectName = "Product " + Math.exp(Math.random());
        JSONObject obj = new JSONObject();
        obj.put("description", projectName);
        obj.put("projectId", projectId);
        return  obj;
    }

    private static JSONObject generateRepository(String repositoryId) {
        JSONObject obj = new JSONObject();
        obj.put("description", DBHelper.generateDescription());
        obj.put("url", "-");
        obj.put("id", repositoryId);
        return  obj;
    }

    private static JSONObject generateCategory(String categoryId) {
        JSONObject obj = new JSONObject();
        obj.put("description", DBHelper.generateDescription());
        obj.put("name", DBHelper.generateName());
        obj.put("id", categoryId);
        return  obj;
    }

    private static JSONObject generateTeam(String teamId) {
        JSONObject obj = new JSONObject();
        obj.put("description", DBHelper.generateDescription());
        obj.put("name", DBHelper.generateName());
        obj.put("id", teamId);
        return  obj;
    }

    private static JSONObject generateRule(String ruleId) {
        JSONObject obj = new JSONObject();
        obj.put("description", DBHelper.generateDescription());
        obj.put("name", DBHelper.generateName());
        obj.put("id", ruleId);
        return  obj;
    }

    public static void updateEntities() {

    }

    public static void printEntities() {

    }
}
