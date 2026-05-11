package appLogic;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginSteps {

    private App app;
    private AppContext appContext;

    @Before
    public void setUp() {
        TestApp.getInstance().reset();
        app = TestApp.getInstance().getApp();
        appContext = app.getAppContext();
    }

    @Given("I am logged in as an employee or a project leader with the initials {string}")
    public void loggedInAsEither(String initials) {
        appContext.login(initials);
        assertTrue(app.isUserLoggedIn(), "User was not logged in successfully");
    }

    @Given("I am logged in as an employee with the initials {string}")
    public void loggedInAsEmployee(String initials) {
        appContext.login(initials);
        assertTrue(app.isUserLoggedIn(), "User was not logged in successfully");
        assertTrue(app.getLoggedInUser().getLeaderProjects().isEmpty(),
                "Expected a plain employee but " + initials + " is a project leader");
    }

    @Given("I am logged in as a project leader with the initials {string}")
    public void loggedInAsProjectLeader(String initials) {
        appContext.login(initials);
        assertTrue(app.isUserLoggedIn(), "User was not logged in successfully");
        assertFalse(app.getLoggedInUser().getLeaderProjects().isEmpty(),
                "Expected a project leader but " + initials + " has no leader projects");
    }
}
