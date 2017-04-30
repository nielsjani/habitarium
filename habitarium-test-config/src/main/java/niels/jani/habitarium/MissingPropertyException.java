package niels.jani.habitarium;

public class MissingPropertyException extends RuntimeException {

    public MissingPropertyException() {
        super("property");
    }

    public MissingPropertyException(String message) {
        super(message);
    }
}
