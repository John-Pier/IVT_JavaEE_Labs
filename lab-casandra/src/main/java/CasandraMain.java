import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.*;
import com.datastax.oss.driver.api.core.session.Session;

import java.net.InetSocketAddress;

/***
 * To run Docker image:
 *  docker run --name casandra-inst  -p 9042:9042 -p 7000:7000 -p 9160:9160 -p 7199:7199 -d cassandra
 * login -> cassandra
 * to set password -> -e CASSANDRA_PASSWORD=cassandra
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
