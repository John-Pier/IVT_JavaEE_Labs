import java.sql.*;
import java.util.ArrayList;

public class Main {
    private static final String CONNECTION_URL = "jdbc:sqlserver://UncleBobTutorials.mssql.somee.com;" +
            "database=UncleBobTutorials;" +
            "packet size=4096;" +
            "user=JohnPierTw_SQLLogin_1;" +
            "password=gx7tlyua5v;";

    static {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws SQLException {
        try(var connection = DriverManager.getConnection(CONNECTION_URL)) {
            var sqlDatabaseConnection = new SQLDatabaseConnection(connection);

            switch (1) {
                case 1 -> sqlDatabaseConnection.selectUnionResult(Main::printUnionResultTable);
                case 2 -> {
                    var result = sqlDatabaseConnection.addNewUpdateEventEntity(resultSet -> {
                        var updateEvent = new UpdateEvent();
                        System.out.println("Выберите CourseId записи.");

                        var idsArray = printCourseTableAndCollectIds(resultSet);

                        System.out.print("Введите CourseId: ");
                        var courseId = input.nextInt();
                        if(!idsArray.contains(courseId)) {
                            System.out.println("Выбран несуществующий CourseId");
                            updateEvent.setCourseId(null);
                        } else {
                            updateEvent.setCourseId(courseId);
                        }

                        System.out.print("Введите Text: ");
                        input.nextLine();
                        updateEvent.setText(input.nextLine());
                        System.out.print("Введите DateTime [yyyy-dd-MM]: ");
                        var date = input.nextLine();
                        updateEvent.setDateTime(date);

                        return updateEvent;
                    });
                    System.out.println("Строк успешно добавлено: " + result);
                }
                case 3 -> {
                    System.out.print("Введите CourseName: ");
                    input.nextLine();
                    var courseName = input.nextLine();
                    System.out.print("Введите Description: ");
                    var description = input.nextLine();
                    var course = new Course(courseName, description);
                    var result = sqlDatabaseConnection.addNewCourseEntity(course);
                    System.out.println("Строк успешно добавлено: " + result);
                }
                case 4 -> {
                    var result = sqlDatabaseConnection.deleteUpdateEventEntity(resultSet -> {
                        var idsArray = printUpdateEventTableAndCollectIds(resultSet);
                        System.out.print("Введите UpdateId записи для удаления: ");
                        var updateId = input.nextInt();
                        if (!idsArray.contains(updateId)) {
                            System.out.println("Введен неверный UpdateId. Выполнение операция прекращено. ");
                            return null;
                        }
                        return updateId;
                    });
                    System.out.println("Строк успешно удалено: " + result);
                }
                case 5 -> {
                    var result = sqlDatabaseConnection.deleteCourseEntity(resultSet -> {
                        var idsArray = printCourseTableAndCollectIds(resultSet);
                        System.out.print("Введите CourseId записи для удаления: ");
                        var courseId = input.nextInt();
                        if (!idsArray.contains(courseId)) {
                            System.out.println("Введен неверный CourseId. Выполнение операция прекращено. ");
                            return null;
                        }

                        var relatedIdsArray =  getRelatedIdsArrayFromUpdateEvents(sqlDatabaseConnection, courseId);

                        if (relatedIdsArray.size() != 0) {
                            System.out.println("Удаление возможно только с удалением связанных сущностей с CourseId = " + courseId);
                            System.out.print("Удалить UpdateEvent(s) ? (1 - да): ");
                            boolean deleteRelated = input.nextInt() == 1;

                            if (deleteRelated) {
                                int deleted = relatedIdsArray.stream().mapToInt(integer -> sqlDatabaseConnection.deleteUpdateEventEntity(items -> integer)).sum();
                                System.out.println("Удалено строк UpdateEvent: " + deleted);
                            } else {
                                System.out.println("Выполнение операция прекращено. ");
                                return null;
                            }
                        }

                        return courseId;
                    });
                    System.out.println("Строк успешно удалено: " + result);
                }
                case 0 -> {
                    input.close();
                    isExit = true;
                }
            }

            System.out.println("\nОсуществляется выход...");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static ArrayList<Integer> getRelatedIdsArrayFromUpdateEvents(SQLDatabaseConnection connection, Integer courseId) {
        var relatedIdsArray = new ArrayList<Integer>();
        connection.selectUpdateEventResult(updateEventSet -> {
            try {
                while (updateEventSet.next()) {
                    if(updateEventSet.getInt(2) == courseId) {
                        relatedIdsArray.add(updateEventSet.getInt(1));
                    }
                }
            } catch (SQLException throwable) {
                throwable.printStackTrace();
            }
        });
        return relatedIdsArray;
    }

    private static ArrayList<Integer> printCourseTableAndCollectIds(ResultSet resultSet) {
        var idsArray = new ArrayList<Integer>();
        System.out.format("%8s\t%18s\t%42s\n", "CourseId", "CourseName", "Description");
        System.out.format(
                "%8s\t%18s\t%42s\n",
                "________",
                "__________________",
                "_________________________________________"
        );
        try {
            while (resultSet.next()) {
                idsArray.add(resultSet.getInt(1));
                System.out.format(
                        "%8s\t%18s\t%42s\n",
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3)
                );
                System.out.println();
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return idsArray;
    }

    private static ArrayList<Integer> printUpdateEventTableAndCollectIds(ResultSet resultSet) {
        var idsArray = new ArrayList<Integer>();
        System.out.format("%8s\t%8s\t%42s\t%14s\n", "UpdateId", "CourseId", "Text", "DateTime");
        System.out.format(
                "%8s\t%8s\t%42s\t%18s\n",
                "________",
                "________",
                "_________________________________________",
                "__________________"
        );

        try {
            while (resultSet.next()) {
                idsArray.add(resultSet.getInt(1));
                System.out.format(
                        "%8s\t%8s\t%16s\t%18s",
                        resultSet.getInt(1),
                        resultSet.getInt(2),
                        resultSet.getString(3),
                        resultSet.getDate(4)
                );
                System.out.println();
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return idsArray;
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
