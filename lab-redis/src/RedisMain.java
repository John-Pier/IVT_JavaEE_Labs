import java.util.logging.Logger;

public class RedisMain {
    private static final Logger LOGGER = Logger.getLogger(RedisMain.class.getName());

    public static void main(String[] args) throws Exception {
        if(args.length < 2) {
            throw new Exception("Нет обязательных аргументов!");
        }
        LOGGER.info("\nlink: "+ args[0] + ",\nusername: ***,\npass: " + args[1]+ "\n");

    }

    public static void insertValues() {

    }

    public static void updateEntities() {

    }

    public static void printEntities() {

    }
}
