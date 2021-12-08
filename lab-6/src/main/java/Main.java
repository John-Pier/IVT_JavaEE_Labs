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
            var statement = connection.createStatement();

            var resultSet = statement.executeQuery(
                    "select count(COMPOSITION.NAME), ALBUM.NAME from COMPOSITION JOIN ALBUM ON composition.album_id = album.id GROUP BY ALBUM.id HAVING count(COMPOSITION.NAME) >= 5"
            );
            printQueryResult(resultSet);

            testOperations(connection);

            System.out.println("\nОсуществляется выход...");
            statement.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void testOperations(Connection connection) throws SQLException {
        var artistName = "updateMe";
        var artistNextName = "deleteMe";

        var statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

        var preparedInsertStatement = connection.prepareStatement("INSERT INTO  ARTIST(NAME) VALUES (?)");
        var preparedUpdateStatement = connection.prepareStatement("UPDATE ARTIST SET  NAME = ? WHERE NAME = ?");
        var preparedDeleteStatement = connection.prepareStatement("DELETE  FROM  ARTIST WHERE NAME = ?");

        System.out.println("\nДобавление " + artistName);
        preparedInsertStatement.setString(1, artistName);
        preparedInsertStatement.executeUpdate();
        printArtistTable(statement.executeQuery("SELECT * FROM ARTIST"));

        System.out.println("\nОбновление " + artistName + "->" + artistNextName);
        preparedUpdateStatement.setString(1, artistNextName);
        preparedUpdateStatement.setString(2, artistName);
        preparedUpdateStatement.executeUpdate();
        printArtistTable(statement.executeQuery("SELECT * FROM ARTIST"));

        System.out.println("\nУдаление " + artistNextName);
        preparedDeleteStatement.setString(1, artistNextName);
        preparedDeleteStatement.executeUpdate();
        printArtistTable(statement.executeQuery("SELECT * FROM ARTIST"));
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

    private static void printQueryResult(ResultSet resultSet) {
        System.out.format("%12s\t%20s\n", "Count of Comp", "Album Name");
        System.out.format(
                "%14s\t%20s\n",
                "______________",
                "____________________"
        );
        try {
            while (resultSet.next()) {
                System.out.format(
                        "%14s\t%20s\n",
                        resultSet.getInt(1),
                        resultSet.getString(2)
                );
                System.out.println();
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }
}
