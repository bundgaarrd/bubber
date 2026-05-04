package appLogic;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;

/**
 * Scenario: Viewing the overall schedule of activities
 */
public class OverallScheduleSteps {
    private Exception exception;

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
