import com.clickhouse.jdbc.ClickHouseDataSource;
import helpers.DBHelper;

import java.sql.*;
import java.util.Properties;

public class Main {
    public static DBHelper dbHelper;

    public static void main(String[] args) throws SQLException {
        if (args.length == 0) {
            return;
        }

        String url = args[0];
        Properties props = new Properties();
        props.setProperty("client_name", "Agent #1");
        props.setProperty("ssl", "false");

        ClickHouseDataSource dataSource = new ClickHouseDataSource(url, props);
        ///john_pier@localhost
        try (var connection = dataSource.getConnection("default", "")) {
            var statement = connection.createStatement();
            dbHelper = new DBHelper(connection);

            System.out.println(connection.isClosed());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
