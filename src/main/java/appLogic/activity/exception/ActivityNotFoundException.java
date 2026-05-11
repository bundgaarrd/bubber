package appLogic.activity.exception;

public class ActivityNotFoundException extends IllegalStateException {
    public ActivityNotFoundException(String message) {
        super(message);
    }
}
