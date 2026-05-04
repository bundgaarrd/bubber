package appLogic;

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
            TestApp.getInstance().getProject().getActivities();
        } catch (Exception e) {
            exception = e;
        }
    }

    @Then("access is denied")
    public void accessIsDenied() {
        Exception check = exception;
        exception = null;
        Assertions.assertNotNull(check, "Expected an exception to be thrown when an employee tries to access the overall schedule, but no exception was thrown: " + check);
    }
    

    @Then("access is approved")
    public void accessIsApproved() {
        Exception check = exception;
        exception = null;
        Assertions.assertNull(check, "Expected no exception to be thrown when a project leader tries to access the overall schedule, but an exception was thrown: " + check);
    }
    @Then("I can view the schedule of all employees")
    public void iCanViewTheScheduleOfAllEmployees() {
        try {
            TestApp.getInstance().getProject().getActivities();
        } catch (Exception e) {
            Assertions.fail("Expected to be able to view the schedule of all employees, but an exception was thrown: " + e.getMessage());
        }
    }

    @When("I attempt to access my own schedule")
    public void iAttemptToAccessMyOwnSchedule() {
        Employee employee = TestApp.getInstance().getApp().getLoggedInUser();
        try {
            employee.getActivities();
        } catch (Exception e) {
            exception = e;
        }
    }
    @Then("I can view my own schedule")
    public void iCanViewMyOwnSchedule() {
        Exception check = exception;
        exception = null;
        Assertions.assertNull(check, "Expected to be able to view my own schedule, but an exception was thrown: " + check);
    }
}
