package main;

public class ReverseException extends Exception {

    public ReverseException(String message) {
        super(message);
    }

    public ReverseException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReverseException(Throwable cause) {
        super(cause);
    }

}
