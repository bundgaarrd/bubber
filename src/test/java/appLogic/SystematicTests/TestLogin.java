package appLogic.SystematicTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import appLogic.App;

public class TestLogin {
    @AfterEach
    void reset() {
        App.resetInstanceForTests();
    }

    @Test
    void login_nullInitials_throwsIllegalArgument() {
        App app = App.getInstance();
        assertThrows(IllegalArgumentException.class, () -> app.login(null));
    }

    @Test
    void login_tooLongInitials_throwsIllegalArgument() {
        App app = App.getInstance();
        assertThrows(IllegalArgumentException.class, () -> app.login("ABCDE"));
    }

    @Test
    void login_unknownEmployee_throwsIllegalState() {
        App app = App.getInstance();
        assertThrows(IllegalStateException.class, () -> app.login("zzzz"));
    }

    @Test
    void login_validEmployee_setsLoggedInUser() {
        App app = App.getInstance();
        app.login("huba");
        assertTrue(app.isUserLoggedIn());
        assertEquals("huba", app.getLoggedInUser().getInitials());
    }
}
