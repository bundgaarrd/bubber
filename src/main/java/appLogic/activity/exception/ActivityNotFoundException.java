package appLogic.activity.exception; // Lavet af Valdemar (s246575)

public class ActivityNotFoundException extends IllegalStateException {
    public ActivityNotFoundException(String message) {
        super(message);
    }
}
