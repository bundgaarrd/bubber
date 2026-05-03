package appLogic;

import static org.junit.jupiter.api.Assertions.assertTrue;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;

public class LoginSteps {

    private App app;
    private Activity selectedActivity;
    private Exception thrownException;

    @Before
    public void setUp() {
        TestApp.getInstance().reset();
        app = TestApp.getInstance().getApp();
    }

   @Given("I am logged in as an employee or a project leader with the initials {string}")
    public void loggedInWithInitials(String initials) {
        app.login(initials);
        assertTrue(app.isUserLoggedIn(), "User was not logged in successfully");
    }
    
}
