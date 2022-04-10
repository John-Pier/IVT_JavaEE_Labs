import com.clickhouse.jdbc.ClickHouseDataSource;

import java.sql.*;
import java.util.Properties;

public class Main {
    public static void main(String[] args) throws SQLException {
        if(args.length == 0) {
            return;
        }

        String url = "jdbc:ch://localhost/projects_systems"; //args[0];
        Properties props = new Properties();
       // props.setProperty("client_name","Agent #1");
       // props.setProperty("db","projects_systems");
//        props.setProperty("password","299792458");
       // props.setProperty("ssl", "false");

        ClickHouseDataSource dataSource = new ClickHouseDataSource(url, props);

        try(var connection = dataSource.getConnection("default", "299792458")) {
            System.out.println(connection.isClosed());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
