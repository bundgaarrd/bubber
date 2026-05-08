package appLogic.activity.exception;

public class UnavailableEmployeeException extends IllegalStateException {
    public UnavailableEmployeeException(String message) {
        super(message);
    }
}
