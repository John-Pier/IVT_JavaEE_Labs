package johnpier.exeptions;

public class DuplicateModelNameException extends Exception {
    public DuplicateModelNameException(String errorMessage) {
        super(errorMessage);
    }
}
