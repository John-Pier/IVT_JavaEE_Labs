package helpers;

import java.sql.*;

public class DBHelper {

    private final Connection connection;

    public DBHelper(Connection connection) {
        this.connection = connection;
    }

    public static String generateDescription(){
        return "Generated Description " + Math.random();
    }

    public static String generateName(){
        return "Generated Name " + Math.random();
    }

    public int selectTeamId(String name) {
        return selectIdByParam(name, "SELECT * FROM projects_systems.public.\"Team\" WHERE name = ?");
    }

    public int selectPositionId(String name) {
        return selectIdByParam(name, "SELECT * FROM projects_systems.public.\"Position\" WHERE name = ?");
    }

    public int selectEmployeeId(String name) {
        return selectIdByParam(name, "SELECT * FROM projects_systems.public.\"Employee\" WHERE name = ?");
    }

    public int selectCategoriesId(String name) {
        return selectIdByParam(name, "SELECT * FROM projects_systems.public.\"P_Categories\" WHERE name = ?");
    }

    public int selectRuleId(String name) {
        return selectIdByParam(name, "SELECT * FROM projects_systems.public.\"Rule\" WHERE name = ?");
    }

    public int selectRoleId(String name) {
        return selectIdByParam(name, "SELECT * FROM projects_systems.public.\"Role\" WHERE name = ?");
    }

    public int selectSkillId(String name) {
        return selectIdByParam(name, "SELECT * FROM projects_systems.public.\"Skill\" WHERE name = ?");
    }

    private int selectIdByParam(String param, String query) {
        try {
            var statement = connection.prepareStatement(query);
            statement.setString(1, param);
            var result = statement.executeQuery();
            if (result.next()) {
                return result.getInt(1);
            }
            return -1;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int selectFirstRepositoryId() {
        return selectFirstId("SELECT * FROM projects_systems.public.\"Repository\" ORDER BY id DESC");
    }

    public int selectFirstProjectId() {
        return selectFirstId("SELECT * FROM projects_systems.public.\"Projects\" ORDER BY id DESC");
    }

    private int selectFirstId(String query) {
        try {
            var statement = connection.prepareStatement(query);
            var result = statement.executeQuery();
            if (result.next()) {
                return result.getInt(1);
            }
            return -1;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
