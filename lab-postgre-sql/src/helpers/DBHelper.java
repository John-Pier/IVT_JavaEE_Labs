package helpers;

import java.sql.*;

public class DBHelper {

    private final Connection connection;

    public DBHelper(Connection connection) {
        this.connection = connection;
    }

    public static String generateDescription(){
        return "Generated Description " + Math.exp(Math.random());
    }

    public static String generateName(){
        return "Generated Name " + Math.exp(Math.random());
    }

    public int selectProjectId(String description) {
        try {
            var statement = connection.prepareStatement("SELECT * FROM projects_systems.public.\"Projects\" WHERE description == ?");
            statement.setString(1, description);
            var result = statement.executeQuery();
            return result.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int selectTeamId(String name) {
        return selectIdByName(name, "SELECT * FROM projects_systems.public.\"Team\" WHERE name == ?");
    }

    public int selectPositionId(String name) {
        return selectIdByName(name, "SELECT * FROM projects_systems.public.\"Position\" WHERE name == ?");
    }

    public int selectEmployeeId(String name) {
        return selectIdByName(name, "SELECT * FROM projects_systems.public.\"Employee\" WHERE name == ?");
    }

    public int selectCategoriesId(String name) {
        return selectIdByName(name, "SELECT * FROM projects_systems.public.\"P_Categories\" WHERE name == ?");
    }

    public int selectRuleId(String name) {
        return selectIdByName(name, "SELECT * FROM projects_systems.public.\"Rule\" WHERE name == ?");
    }

    public int selectRoleId(String name) {
        return selectIdByName(name, "SELECT * FROM projects_systems.public.\"Role\" WHERE name == ?");
    }

    public int selectSkillId(String name) {
        return selectIdByName(name, "SELECT * FROM projects_systems.public.\"Skill\" WHERE name == ?");
    }

    public int selectIdByName(String name, String query) {
        try {
            var statement = connection.prepareStatement(query);
            statement.setString(1, name);
            var result = statement.executeQuery();
            return result.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int selectRepositoryIdByDescription(String description) {
        return selectIdByName(description, "SELECT * FROM projects_systems.public.\"Repository\" WHERE description == ?");
    }


}
