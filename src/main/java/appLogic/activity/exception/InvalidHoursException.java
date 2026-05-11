package appLogic.activity.exception; // Lavet af Valdemar (s246575)

public class InvalidHoursException extends IllegalArgumentException {
    public InvalidHoursException(String message) {
        super(message);
    }
}
