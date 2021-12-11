public interface Test {
    default void gas() {
        System.out.println("Газ!");
    }
}
interface Car {
    default void gas() {
        System.out.println("Газ!");
    }
}

