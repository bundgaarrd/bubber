package appLogic.activity.exception;

public class DuplicateActivityException extends IllegalStateException {
    public DuplicateActivityException(String message) {
        super(message);
    }
}
