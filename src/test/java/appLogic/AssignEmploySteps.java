package appLogic;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AssignEmploySteps {

    private App app;
    private Activity selectedActivity;
    private Exception thrownException;

    @Before
    public void setUp() {
        TestApp.getInstance().reset();
        app = TestApp.getInstance().getApp();
    }

   @Given("I am logged in as an employee or a project leader")
    public void loggedIn() {
        assertTrue(app.isAdminLoggedIn(), "User is not logged in");
    }
}
