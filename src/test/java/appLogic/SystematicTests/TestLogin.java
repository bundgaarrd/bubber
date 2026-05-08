package appLogic.SystematicTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import appLogic.AppContext;
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
        AppContext appContext = App.getInstance().getAppContext();
        assertThrows(IllegalArgumentException.class, () -> appContext.login(null));
    }

    @Test
    void login_tooLongInitials_throwsIllegalArgument() {
        AppContext appContext = App.getInstance().getAppContext();
        assertThrows(IllegalArgumentException.class, () -> appContext.login("ABCDE"));
    }

    @Test
    void login_unknownEmployee_throwsIllegalState() {
        AppContext appContext = App.getInstance().getAppContext();
        assertThrows(IllegalStateException.class, () -> appContext.login("zzzz"));
    }

    @Test
    void login_validEmployee_setsLoggedInUser() {
        App app = App.getInstance();
        AppContext appContext = app.getAppContext();
        appContext.login("huba");
        assertTrue(app.isUserLoggedIn());
        assertEquals("huba", app.getLoggedInUser().getInitials());
    }
}
