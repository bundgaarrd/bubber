package appLogic;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Scenario: Viewing the overall schedule of activities
 */
public class OverallScheduleSteps {
    private Exception exception;

    @Before
    public void setup() {
        TestApp.getInstance().getApp().setAdminLoggedIn(false);
    }

    @Given("I am logged in as an employee")
    public void iAmLoggedInAsAnEmployee() {
        boolean admin = TestApp.getInstance().getApp().isAdminLoggedIn();
        Assertions.assertFalse(admin, "User is not logged in as an employee.");
    }

    @When("I attempt to access overview of all employee schedules")
    public void iAttemptToAccessOverviewOfAllEmployeeSchedules() {
        try {
            TestApp.getInstance().getApp().getAllActivities();
        } catch (Exception e) {
            exception = e;
        }
    }

    @Then("access is denied")
    public void accessIsDenied() {
        Assertions.assertNotNull(exception, "Expected an exception to be thrown when an employee tries to access the overall schedule, but no exception was thrown.");
    }

    @Given("I am logged in as a project leader")
    public void iAmLoggedInAsAProjectLeader() {
        Employee currentUser = TestApp.getInstance().getApp().getCurrentUser();
        boolean isLeader = !currentUser.getLeaderProjects().isEmpty();
        Assertions.assertTrue(isLeader, "User is not logged in as a project leader.");
    }
    @Then("access is approved")
    public void accessIsApproved() {
        Assertions.assertNull(exception, "Expected no exception to be thrown when a project leader tries to access the overall schedule, but an exception was thrown: " + exception.getMessage());
    }
    @Then("I can view the schedule of all employees")
    public void iCanViewTheScheduleOfAllEmployees() {
        try {
            TestApp.getInstance().getApp().getAllActivities();
        } catch (Exception e) {
            Assertions.fail("Expected to be able to view the schedule of all employees, but an exception was thrown: " + e.getMessage());
        }
    }
}
