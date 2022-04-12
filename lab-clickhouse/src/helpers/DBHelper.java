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

    public String selectTeamId(String name) {
        return selectIdByParam(name, "SELECT * FROM projects_systems.team WHERE name = ?");
    }

    public String selectPositionId(String name) {
        return selectIdByParam(name, "SELECT * FROM projects_systems.position WHERE name = ?");
    }

    public String selectEmployeeId(String name) {
        return selectIdByParam(name, "SELECT * FROM projects_systems.employee WHERE name = ?");
    }

    public String selectCategoriesId(String name) {
        return selectIdByParam(name, "SELECT * FROM projects_systems.p_categories WHERE name = ?");
    }

    public String selectRuleId(String name) {
        return selectIdByParam(name, "SELECT * FROM projects_systems.rule WHERE name = ?");
    }

    public String selectRoleId(String name) {
        return selectIdByParam(name, "SELECT * FROM projects_systems.role WHERE name = ?");
    }

    public String selectSkillId(String name) {
        return selectIdByParam(name, "SELECT * FROM projects_systems.skill WHERE name = ?");
    }

    private String selectIdByParam(String param, String query) {
        try {
            var statement = connection.prepareStatement(query);
            statement.setString(1, param);
            var result = statement.executeQuery();
            if (result.next()) {
                return result.getString(1);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String selectFirstRepositoryId() {
        return selectFirstId("SELECT * FROM projects_systems.repository ORDER BY description DESC");
    }

    public String selectFirstProjectId() {
        return selectFirstId("SELECT * FROM projects_systems.projects");
    }

    private String selectFirstId(String query) {
        try {
            var statement = connection.prepareStatement(query);
            var result = statement.executeQuery();
            if (result.next()) {
                return result.getString(1);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
