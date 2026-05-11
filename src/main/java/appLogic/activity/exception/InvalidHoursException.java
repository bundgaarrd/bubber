package appLogic.activity.exception;

public class InvalidHoursException extends IllegalArgumentException {
    public InvalidHoursException(String message) {
        super(message);
    }
}
