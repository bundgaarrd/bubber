package appLogic.SystematicTests;

import appLogic.App;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class AppTest {
    @AfterEach
    void reset() {
        App.resetInstanceForTests();
    }

    @Test
    void getInstance_returnsSameInstance() {
        App a1 = App.getInstance();
        App a2 = App.getInstance();
        assertSame(a1, a2);
    }
}