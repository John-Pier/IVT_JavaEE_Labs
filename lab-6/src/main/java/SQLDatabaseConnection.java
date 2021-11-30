import java.sql.*;

public class SQLDatabaseConnection {
    private final Connection connection;

    public SQLDatabaseConnection(Connection connection) {
        this.connection = connection;
    }

    public void selectUnionResult(Action<ResultSet> action) {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM UpdateEvent JOIN Course ON UpdateEvent.CourseId = Course.CourseId");
            action.run(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void selectUpdateEventResult(Action<ResultSet> action) {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM UpdateEvent");
            action.run(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int addNewUpdateEventEntity(ActionWithResult<ResultSet, UpdateEvent> action) {
        try (
                Statement courseStatement = connection.createStatement();
                Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)
        ) {
            ResultSet coursesSet = courseStatement.executeQuery("SELECT * FROM Course");
            ResultSet resultSet = statement.executeQuery("SELECT * FROM UpdateEvent");

            var updateEvent = action.run(coursesSet);

            if(updateEvent == null) {
                return 0;
            }

            resultSet.moveToInsertRow();
            if(updateEvent.getCourseId() != null) {
                resultSet.updateInt(2, updateEvent.getCourseId());
            }
            resultSet.updateString(3, updateEvent.getText());
            resultSet.updateString(4, updateEvent.getDateTime());
            resultSet.insertRow();
            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public int addNewCourseEntity(Course course) {
        try (Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Course");
            resultSet.moveToInsertRow();
            resultSet.updateString(2, course.getCourseName());
            resultSet.updateString(3, course.getDescription());
            resultSet.insertRow();
            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int deleteUpdateEventEntity(ActionWithResult<ResultSet, Integer> action) {
        try (Statement statement = connection.createStatement()) {
            var deleteStatement = connection.prepareStatement("DELETE FROM UpdateEvent WHERE UpdateId = ?");
            ResultSet resultSet = statement.executeQuery("SELECT * FROM UpdateEvent");
            var updateId = action.run(resultSet);
            if (updateId == null) {
                return 0;
            }
            deleteStatement.setInt(1, updateId);
            return deleteStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int deleteCourseEntity(ActionWithResult<ResultSet, Integer> action) {
        try (Statement statement = connection.createStatement()) {
            var deleteStatement = connection.prepareStatement("DELETE FROM Course WHERE CourseId = ?");
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Course");
            var courseId = action.run(resultSet);
            if (courseId == null) {
                return 0;
            }
            deleteStatement.setInt(1, courseId);
            return deleteStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}

//            String sql = updateEvent.getCourseId() != null
//                    ? "INSERT UpdateEvent(CourseId, Text, DateTime) VALUES ('" + updateEvent.getCourseId() + "', '" + updateEvent.getText() + "', '" + updateEvent.getDateTime() + "')"
//                    : "INSERT UpdateEvent(Text, DateTime) VALUES ('" + updateEvent.getText() + "', '" + updateEvent.getDateTime() + "')";
//            return statement.executeUpdate(sql);
//            return statement.executeUpdate("INSERT Course(CourseName, Description) VALUES ('" +
//                    course.getCourseName() + "', '" +
//                    course.getDescription() + "')");