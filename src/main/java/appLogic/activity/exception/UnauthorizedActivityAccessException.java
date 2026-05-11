package appLogic.activity.exception;

public class UnauthorizedActivityAccessException extends IllegalStateException {
    public UnauthorizedActivityAccessException(String message) {
        super(message);
    }
}
