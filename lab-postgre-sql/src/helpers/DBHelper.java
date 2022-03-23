package helpers;

public class DBHelper {
    public static String generateDescription(){
        return "Generated Description " + Math.exp(Math.random());
    }

    public static String generateName(){
        return "Generated Name " + Math.exp(Math.random());
    }
}
