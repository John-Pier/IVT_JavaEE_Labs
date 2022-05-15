import redis.clients.jedis.*;

/***
 * To run Docker image:  docker run -p 6379:6379 -d redis
 */

public class RedisMain {

    public static void main(String[] args) throws Exception {
        JedisPool pool = new JedisPool("localhost", 6379);
        try(var jedis = pool.getResource()) {
            System.out.println(jedis.isConnected());
            System.out.println(jedis.clientGetname());
            jedis.set("test", String.valueOf(23));
        }
        pool.close();
    }

    public static void insertValues() {

    }

    public static void updateEntities() {

    }

    public static void printEntities() {

    }
}
