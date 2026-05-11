package appLogic.activity.exception; // Lavet af Valdemar (s246575)

public class DuplicateActivityException extends IllegalStateException {
    public DuplicateActivityException(String message) {
        super(message);
    }
}
