package appLogic.activity.exception; // Lavet af Valdemar (s246575)

public class UnavailableEmployeeException extends IllegalStateException {
    public UnavailableEmployeeException(String message) {
        super(message);
    }
}
