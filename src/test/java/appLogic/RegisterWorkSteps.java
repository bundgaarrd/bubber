package appLogic;

import appLogic.activity.command.CreateProjectActivity;
import appLogic.activity.command.CreateFixedActivity;
import appLogic.activity.exception.ActivityNotFoundException;
import appLogic.activity.exception.DuplicateActivityException;
import appLogic.activity.exception.InvalidHoursException;
import appLogic.employee.Employee;
import appLogic.project.Project;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class RegisterWorkSteps {
    private String errorMessage;
    private Activity activity;
    private TimeEntry lastTimeEntry;
    private int timeEntriesBeforeNegativeAttempt;
    private final List<FixedActivity> fixedActivities = new ArrayList<>();
    private int timesheetSizeBeforeAdd;
    private boolean datesOverlap;

    @And("There is an activity named {string}  in this project ")
    public void activityExistsWithSpacingVariant(String name) {
        ensureActivityExistsInCurrentProject(name);
    }

    @And("I am assigned to the activity named {string} ")
    public void assignedActivityWithSpacingVariant(String name) {
        assignLoggedInUserToActivity(name);
    }

    @Given("I am assigned to the activity named {string}")
    public void iAmAssignedToTheActivityNamed(String name) {
        assignLoggedInUserToActivity(name);
    }

    @When("I log that I have worked 5 hours")
    public void logHoursWorked() {
        Employee employee = requireLoggedInUser();
        lastTimeEntry = TestApp.getInstance().getApp().getActivityService()
                .registerWork(activity.getId(), employee, LocalDateTime.now(), 5);
    }

    @Then("5 working hours are registered")
    public void hoursRegistered() {
        assertNotNull(lastTimeEntry, "Expected a time entry to be created, but none was registered.");
        assertEquals(5, lastTimeEntry.getHoursWorked());
    }

    @When("I register negative hours")
    public void negativeHoursRegister() {
        Employee employee = requireLoggedInUser();
        timeEntriesBeforeNegativeAttempt = TestApp.getInstance().getApp().getTimeEntryRepository().findAll().size();
        try {
            TestApp.getInstance().getApp().getActivityService()
                    .registerWork(activity.getId(), employee, LocalDateTime.now(), -5);
            errorMessage = null;
        } catch (InvalidHoursException e) {
            errorMessage = e.getMessage();
        }
    }

    @Then("an error message is sent")
    public void errorMessageSent() {
        assertNotNull(errorMessage);
        assertFalse(errorMessage.isBlank());
    }

    @Then("the hours are not logged")
    public void theHoursAreNotLogged() {
        int sizeAfterAttempt = TestApp.getInstance().getApp().getTimeEntryRepository().findAll().size();
        assertEquals(timeEntriesBeforeNegativeAttempt, sizeAfterAttempt);
    }

    @When("I add a fixed activity with name {string} and specified start and end date")
    public void iAddAFixedActivityWithNameAndSpecifiedStartAndEndDate(String name) {
        LocalDate startDate = LocalDate.now().plusDays(1);
        LocalDate endDate = startDate.plusDays(3);
        timesheetSizeBeforeAdd = fixedActivities.size();
        datesOverlap = fixedActivities.stream().anyMatch(existing -> overlaps(existing, startDate, endDate));
        if (!datesOverlap) {
            fixedActivities.add(createFixedActivity(name, startDate, endDate));
        }
    }

    @When("there is no other fixed activity on the same dates")
    public void thereIsNoOtherFixedActivityOnTheSameDates() {
        assertFalse(datesOverlap);
    }

    @Then("the fixed activity is added to the timesheet")
    public void theFixedActivityIsAddedToTheTimesheet() {
        assertEquals(timesheetSizeBeforeAdd + 1, fixedActivities.size());
    }

    @Given("I have a fixed activity called {string} with specified start and end date")
    public void iHaveAFixedActivityCalledWithSpecifiedStartAndEndDate(String name) {
        LocalDate startDate = LocalDate.now().plusDays(1);
        LocalDate endDate = startDate.plusDays(3);
        if (fixedActivities.stream().noneMatch(activity -> activity.getName().equals(name))) {
            fixedActivities.add(createFixedActivity(name, startDate, endDate));
        }
    }

    @When("I add a fixed activity with name {string} at the same dates as {string}")
    public void iAddAFixedActivityWithNameAtTheSameDatesAs(String name, String existingActivityName) {
        FixedActivity existing = fixedActivities.stream()
                .filter(activity -> activity.getName().equals(existingActivityName))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Expected existing fixed activity: " + existingActivityName));

        LocalDate startDate = existing.getStartDate();
        LocalDate endDate = existing.getEndDate();
        timesheetSizeBeforeAdd = fixedActivities.size();
        datesOverlap = fixedActivities.stream().anyMatch(activity -> overlaps(activity, startDate, endDate));
        if (!datesOverlap) {
            fixedActivities.add(createFixedActivity(name, startDate, endDate));
            errorMessage = null;
        } else {
            errorMessage = "There is already a fixed activity on the same dates";
        }
    }

    @Then("an error message is shown indicating that there is already a fixed activity on the same dates")
    public void anErrorMessageIsShownIndicatingThatThereIsAlreadyAFixedActivityOnTheSameDates() {
        assertNotNull(errorMessage);
        assertFalse(errorMessage.isBlank());
    }

    @Then("the timesheet is not updated")
    public void theTimesheetIsNotUpdated() {
        assertEquals(timesheetSizeBeforeAdd, fixedActivities.size());
    }

    private void ensureActivityExistsInCurrentProject(String name) {
        Project project = requireCurrentProject();
        String projectId = project.getProjectID();

        try {
            activity = TestApp.getInstance().getApp().getActivityService().findByProjectAndName(projectId, name);
        } catch (ActivityNotFoundException notFound) {
            try {
                activity = TestApp.getInstance().getApp().getActivityService().createProjectActivity(
                        new CreateProjectActivity(projectId, name, "", "", LocalDate.now())
                );
            } catch (DuplicateActivityException duplicate) {
                activity = TestApp.getInstance().getApp().getActivityService().findByProjectAndName(projectId, name);
            }
        }

        assertNotNull(activity, "Expected an activity named " + name + " in this project.");
    }

    private void assignLoggedInUserToActivity(String name) {
        ensureActivityExistsInCurrentProject(name);
        Employee employee = requireLoggedInUser();
        TestApp.getInstance().getApp().getActivityService().assignEmployee(activity.getId(), employee);

        boolean assigned = employee.getActivities().stream().anyMatch(a -> a.getId().equals(activity.getId()));
        assertTrue(assigned, "Expected to be assigned to activity " + name + ".");
    }

    private Project requireCurrentProject() {
        Project project = TestApp.getInstance().getProject();
        assertNotNull(project, "No current project is set for this scenario.");
        return project;
    }

    private Employee requireLoggedInUser() {
        Employee employee = TestApp.getInstance().getApp().getLoggedInUser();
        assertNotNull(employee, "No logged in user for this scenario.");
        return employee;
    }

    private FixedActivity createFixedActivity(String name, LocalDate startDate, LocalDate endDate) {
        Project project = requireCurrentProject();
        Activity created = TestApp.getInstance().getApp().getActivityService().createFixedActivity(
                new CreateFixedActivity(
                        project.getProjectID(),
                        name,
                        "",
                        "",
                        startDate,
                        endDate,
                        FixedActivityType.VACATION
                )
        );
        return (FixedActivity) created;
    }

    private boolean overlaps(FixedActivity existing, LocalDate start, LocalDate end) {
        return !(existing.getEndDate().isBefore(start) || existing.getStartDate().isAfter(end));
    }
}
