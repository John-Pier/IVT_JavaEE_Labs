import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.*;
import com.datastax.oss.driver.api.core.session.Session;

import java.net.InetSocketAddress;

/***
 * To run Docker image:
 * docker network create cassandra
 * docker run --rm --network cassandra -e CQLSH_HOST=cassandra -e CQLSH_PORT=9042 -e CQLVERSION=3.4.5 nuvo/docker-cqlsh
 * docker run --rm -d --name cassandra  -p 9042:9042 --hostname cassandra --network cassandra cassandra
 * docker network rm cassandra
 *  -e CASSANDRA_PASSWORD=cassandra
 *  docker run --name casandra-inst  -p 9042:9042 -p 7000:7000 -p 9160:9160 -p 7199:7199 -d cassandra
 * cassandra
 */

public class CasandraMain {

    private static Session session;

    public static void main(String[] args) throws Exception {
        CqlSession session = CqlSession.builder()
                .addContactPoint(new InetSocketAddress("localhost", 9042))
                .withLocalDatacenter("datacenter1")
                .build();

        try (session) {
            CasandraMain.session = session;
            ResultSet rs = session.execute("select release_version from system.local");
            Row row = rs.one();
            System.out.println(row.getString("release_version"));
            System.out.println(session.isClosed());
        }
    }
}
