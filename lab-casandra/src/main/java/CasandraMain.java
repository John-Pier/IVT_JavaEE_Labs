import com.datastax.oss.driver.api.core.session.Session;

/***
 * To run Docker image:
 * docker network create cassandra
 * docker run --rm -d --name cassandra --hostname cassandra --network cassandra cassandra
 * docker network rm cassandra
 * cassandra
 */

public class CasandraMain {

//    private Cluster cluster;

    private Session session;

    public static void main(String[] args) throws Exception {
    }
}
