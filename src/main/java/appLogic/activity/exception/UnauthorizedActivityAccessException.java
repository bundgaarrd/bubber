package appLogic.activity.exception; // Lavet af Valdemar (s246575)

public class UnauthorizedActivityAccessException extends IllegalStateException {
    public UnauthorizedActivityAccessException(String message) {
        super(message);
    }
}
