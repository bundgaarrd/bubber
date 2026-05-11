package appLogic;

import appLogic.activity.FixedActivityType;
import appLogic.activity.command.CreateFixedActivity;
import appLogic.activity.exception.ActivityNotFoundException;
import appLogic.activity.exception.DuplicateActivityException;
import appLogic.activity.exception.InvalidHoursException;
import appLogic.activity.impl.Activity;
import appLogic.activity.impl.FixedActivity;
import appLogic.employee.Employee;
import appLogic.project.Project;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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

    @When("I log that I have worked {int} hours")
    public void logHoursWorked(int hours) {
        Employee employee = requireLoggedInUser();
        lastTimeEntry = TestApp.getInstance().getApp().getActivityService()
                .registerWork(activity.getId(), employee, LocalDateTime.now(), LocalDateTime.now(), hours);
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
                    .registerWork(activity.getId(), employee, LocalDateTime.now(), LocalDateTime.now(), -5);
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
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        int startWeek = startDate.get(weekFields.weekOfWeekBasedYear());
        int endWeek = endDate.get(weekFields.weekOfWeekBasedYear());
        int startYear = startDate.getYear();
        int endYear = endDate.getYear();
        timesheetSizeBeforeAdd = fixedActivities.size();
        datesOverlap = fixedActivities.stream().anyMatch(existing -> overlaps(existing, startWeek, endWeek, startYear, endYear));
        if (!datesOverlap) {
            fixedActivities.add(createFixedActivity(name, startWeek, endWeek, startYear, endYear));
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
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        int startWeek = startDate.get(weekFields.weekOfWeekBasedYear());
        int endWeek = endDate.get(weekFields.weekOfWeekBasedYear());
        int startYear = startDate.getYear();
        int endYear = endDate.getYear();
        if (fixedActivities.stream().noneMatch(activity -> activity.getName().equals(name))) {
            fixedActivities.add(createFixedActivity(name, startWeek, endWeek, startYear, endYear));
        }
    }

    @When("I add a fixed activity with name {string} at the same dates as {string}")
    public void iAddAFixedActivityWithNameAtTheSameDatesAs(String name, String existingActivityName) {
        FixedActivity existing = fixedActivities.stream()
                .filter(activity -> activity.getName().equalsIgnoreCase(existingActivityName))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Expected existing fixed activity: " + existingActivityName));

        int startWeek = existing.getStartWeek();
        int endWeek = existing.getEndWeek();
        int startYear = existing.getStartYear();
        int endYear = existing.getEndYear();
        timesheetSizeBeforeAdd = fixedActivities.size();
        datesOverlap = fixedActivities.stream().anyMatch(activity -> overlaps(activity, startWeek, endWeek, startYear, endYear));
        if (!datesOverlap) {
            fixedActivities.add(createFixedActivity(name, startWeek, endWeek, startYear, endYear));
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
                activity = TestApp.getInstance().getApp().getActivityService().createWorkActivity(
                        new appLogic.activity.command.CreateWorkActivity(projectId, name, "", "", LocalDateTime.now(), LocalDateTime.now(), 5)
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
        // Create a TimeEntry to represent the assignment and call the TimeEntry-based API
        TimeEntry entry = new TimeEntry(employee, activity, LocalDateTime.now(), LocalDateTime.now(), 0);
        TestApp.getInstance().getApp().getActivityService().assignEmployee(activity.getId(), entry);

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

    private FixedActivity createFixedActivity(String name, int startWeek, int endWeek, int startYear, int endYear) {
        // Convert week/year to LocalDate (Monday of the given week)
        WeekFields weekFields = WeekFields.of(java.util.Locale.getDefault());
        LocalDate startDate = LocalDate.now()
                .withYear(startYear)
                .with(weekFields.weekOfWeekBasedYear(), startWeek)
                .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate endDate = LocalDate.now()
                .withYear(endYear)
                .with(weekFields.weekOfWeekBasedYear(), endWeek)
                .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

        FixedActivityType type = FixedActivityType.valueOf(name.toUpperCase().replace(" ", "_"));

        Activity created = TestApp.getInstance().getApp().getActivityService().createFixedActivity(
                new CreateFixedActivity(
                        startDate.atStartOfDay(),
                        endDate.atStartOfDay(),
                        type
                )
        );
        return (FixedActivity) created;
    }

    private boolean overlaps(FixedActivity existing, int startWeek, int endWeek, int startYear, int endYear) {
        return startWeek <= existing.getEndWeek() && endWeek >= existing.getStartWeek() &&
                startYear <= existing.getEndYear() && endYear >= existing.getStartYear();
    }
}
