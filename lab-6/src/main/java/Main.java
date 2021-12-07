import java.sql.*;
import java.util.*;

public class Main {
    private static final String CONNECTION_URL = "jdbc:postgresql://abul.db.elephantsql.com/nenzqwco";

    public static void main(String[] args) throws SQLException {
        Properties props = new Properties();
        props.setProperty("database","nenzqwco");
        props.setProperty("user","nenzqwco");
        props.setProperty("password","Sup1OjDDcnc4_fSopki8lbZzD84Jr3RL");
        props.setProperty("ssl","false");

        try(var connection = DriverManager.getConnection(CONNECTION_URL, props)) {
            var statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            var resultSet = statement.executeQuery("SELECT * FROM ARTIST");

            printArtistTable(resultSet);

            System.out.println("\nОсуществляется выход...");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void printArtistTable(ResultSet resultSet) {
        System.out.format("%8s\t%18s\n", "ID", "Name");
        System.out.format(
                "%8s\t%18s\n",
                "________",
                "__________________"
        );
        try {
            while (resultSet.next()) {
                System.out.format(
                        "%8s\t%18s\n",
                        resultSet.getInt(1),
                        resultSet.getString(2)
                );
                System.out.println();
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

  private static void printUnionResultTable(ResultSet resultSet) {
        System.out.format(
                "%8s\t%8s\t%42s\t%18s\t%18s\t%32s\n",
                "UpdateId",
                "CourseId",
                "Text",
                "DateTime",
                "CourseName",
                "Description"
        );
        System.out.format(
                "%8s\t%8s\t%42s\t%18s\t%18s\t%32s\n",
                "________",
                "________",
                "_________________________________________",
                "__________________",
                "__________________",
                "________________________________"
        );
        try {
            while (resultSet.next()) {
                System.out.format(
                        "%8s\t%8s\t%42s\t%18s\t%18s\t%32s\n",
                        resultSet.getInt(1),
                        resultSet.getInt(2),
                        resultSet.getString(3),
                        resultSet.getDate(4),
                        resultSet.getString(6),
                        resultSet.getString(7)
                );
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }
}
