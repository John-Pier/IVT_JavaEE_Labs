package helpers;

import java.util.Random;

public class DBHelper {

private static final Random random = new Random();

    public static String generateDescription(){
        return "Generated Description " + random.nextInt(10000);
    }

    public static String generateName(){
        return "Generated Name " + random.nextInt(10000);
    }

    public static String generateId(){
        return String.valueOf(random.nextInt(100000));
    }
}
