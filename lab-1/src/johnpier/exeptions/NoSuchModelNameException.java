package johnpier.exeptions;

public class NoSuchModelNameException extends Exception {
    public NoSuchModelNameException(String errorMessage) {
        super(errorMessage);
    }
}