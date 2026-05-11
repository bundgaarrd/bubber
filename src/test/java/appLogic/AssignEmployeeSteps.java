package appLogic;

import appLogic.activity.command.CreateWorkActivity;
import appLogic.activity.exception.ActivityNotFoundException;
import appLogic.activity.exception.DuplicateActivityException;
import appLogic.activity.impl.Activity;
import appLogic.employee.Employee;
import appLogic.project.Project;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class AssignEmployeeSteps {
    private Employee targetEmployee;
    private Activity targetActivity;
    private boolean assignmentSucceeded;
    private String errorMessage;

    @Given("a project {string} with an activity {string} exists")
    public void aProjectWithAnActivityExists(String projectName, String activityName) {
        Project project = TestApp.getInstance().getProjectRegistry().getProjectByName(projectName);
        if (project == null) {
            project = TestApp.getInstance().getProjectRegistry().createProject(projectName);
        }
        TestApp.getInstance().setProject(project);

        String projectId = project.getProjectID();
        try {
            targetActivity = TestApp.getInstance().getApp().getActivityService().findByProjectAndName(projectId, activityName);
        } catch (ActivityNotFoundException e) {
            try {
                targetActivity = TestApp.getInstance().getApp().getActivityService()
                        .createWorkActivity(new CreateWorkActivity(projectId, activityName, "", "", LocalDateTime.now(), LocalDateTime.now(), 5));
            } catch (DuplicateActivityException ignored) {
                targetActivity = TestApp.getInstance().getApp().getActivityService().findByProjectAndName(projectId, activityName);
            }
        }

        assertNotNull(targetActivity);
    }

    @Given("the employee {string} is available")
    public void theEmployeeIsAvailable(String initials) {
        targetEmployee = TestApp.getInstance().getEmployeeByInitials(initials);
        assertNotNull(targetEmployee, "Employee not found: " + initials);
        targetEmployee.setIsAvailable(true);
    }

    @When("{string} is assigned to the activity {string}")
    public void isAssignedToTheActivity(String initials, String activityName) {
        if (targetEmployee == null || !targetEmployee.getInitials().equals(initials)) {
            targetEmployee = TestApp.getInstance().getEmployeeByInitials(initials);
        }
        assertNotNull(targetEmployee, "Employee not found: " + initials);

        ensureCurrentProjectAndActivity(activityName);

        try {
            targetActivity.removeEmployee(targetEmployee);
            // Create a TimeEntry to represent the assignment and call the TimeEntry-based API
            TimeEntry entry = new TimeEntry(targetEmployee, targetActivity, LocalDateTime.now(), LocalDateTime.now(), 0);
            TestApp.getInstance().getApp().getActivityService().assignEmployee(targetActivity.getId(), entry);
            assignmentSucceeded = targetActivity.getAssignedEmployees().contains(targetEmployee);
            errorMessage = null;
        } catch (Exception e) {
            assignmentSucceeded = false;
            errorMessage = "An error occurred while trying to assign the employee: " + e.getMessage();
        }
    }

    @Then("{string} is added to the activity {string}")
    public void isAddedToTheActivity(String initials, String activityName) {
        assertNotNull(targetEmployee);
        assertNotNull(targetActivity);
        assertTrue(assignmentSucceeded, "Expected assignment to succeed.");
        boolean assigned = targetActivity.getAssignedEmployees().stream()
                .anyMatch(employee -> employee.getInitials().equals(initials));
        assertTrue(assigned, "Expected " + initials + " to be assigned to activity " + activityName + ".");
    }

    @Then("the employee {string}'s schedule is updated")
    public void theEmployeeSScheduleIsUpdated(String initials) {
        assertNotNull(targetEmployee);
        assertEquals(initials, targetEmployee.getInitials());
        boolean inSchedule = targetEmployee.getActivities().stream()
                .anyMatch(activity -> activity.getId().equals(targetActivity.getId()));
        assertTrue(inSchedule, "Expected employee schedule to include assigned activity.");
    }

    @Given("the employee {string} is unavailable")
    public void theEmployeeIsUnavailable(String initials) {
        targetEmployee = TestApp.getInstance().getEmployeeByInitials(initials);
        assertNotNull(targetEmployee, "Employee not found: " + initials);
        targetEmployee.setIsAvailable(false);
    }

    @Then("{string} is not added to the activity {string}")
    public void isNotAddedToTheActivity(String initials, String activityName) {
        assertNotNull(targetEmployee);
        assertNotNull(targetActivity);
        assertFalse(assignmentSucceeded, "Expected assignment to fail.");
        boolean assigned = targetActivity.getAssignedEmployees().stream()
                .anyMatch(employee -> employee.getInitials().equals(initials));
        assertFalse(assigned, "Expected " + initials + " to not be assigned to activity " + activityName + ".");
    }

    @Then("an error message indicating the employee is unavailable is displayed")
    public void anErrorMessageIndicatingTheEmployeeIsUnavailableIsDisplayed() {
        assertNotNull(errorMessage);
        assertTrue(errorMessage.toLowerCase().contains("unavailable"));
    }
    private void ensureCurrentProjectAndActivity(String activityName) {
        Project project = TestApp.getInstance().getProject();
        assertNotNull(project, "No current project is set for this scenario. Add feature setup: a project ... with an activity ... exists");

        String projectId = project.getProjectID();
        if (targetActivity == null || !targetActivity.getName().equals(activityName)) {
            targetActivity = TestApp.getInstance().getApp().getActivityService().findByProjectAndName(projectId, activityName);
        }
    }
}
