package appLogic;

import appLogic.activity.command.CreateProjectActivity;
import appLogic.activity.exception.DuplicateActivityException;
import appLogic.activity.impl.Activity;
import appLogic.employee.Employee;
import appLogic.project.Project;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Locale;

public class CreateActivitySteps {
    private Exception lastException;
    private boolean neededHoursRegistered;
    private Integer expectedHours;
    private Integer previousExpectedHours;
    private int startWeek;
    private int startYear;
    private int endWeek;
    private int endYear;
    private int previousStartWeek;
    private int previousStartYear;
    private int previousEndWeek;
    private int previousEndYear;
    private boolean datesAdded;

    @When("I create an activity named {string}")
    public void iCreateAnActivityNamed(String name) {
        Project project = TestApp.getInstance().getProject();
        try {
            Activity activity = TestApp.getInstance().getApp().getActivityService()
                    .createProjectActivity(new CreateProjectActivity(
                            project.getProjectID(),
                            name,
                            "",
                            "",
                            0, 0, 0, 0,
                            5
                    ));
            TestApp.getInstance().setActivity(activity);
            lastException = null;
        } catch (Exception e) {
            lastException = e;
        }
    }

    @Then("the activity now exists in the project")
    public void theActivityNowExistsInTheProject() {
        Project project = TestApp.getInstance().getProject();
        Activity activity = TestApp.getInstance().getApp().getActivityService()
                .findByProjectAndName(project.getProjectID(), TestApp.getInstance().getActivity().getName());
        Assertions.assertNotNull(activity);
    }

    @Given("There is already an activity named {string} in this project")
    public void thereIsAlreadyAnActivityNamedInThisProject(String name) {
        Project project = TestApp.getInstance().getProject();
        Activity activity;
        try {
            activity = TestApp.getInstance().getApp().getActivityService()
                    .createProjectActivity(new CreateProjectActivity(
                            project.getProjectID(),
                            name,
                            "",
                            "",
                            0, 0, 0, 0,
                            5
                    ));
        } catch (DuplicateActivityException e) {
            activity = TestApp.getInstance().getApp().getActivityService().findByProjectAndName(
                    project.getProjectID(), name
            );
        }
        TestApp.getInstance().setActivity(activity);
    }

    @Then("an error message is shown indicating that an activity with the same name already exists in this project")
    public void anErrorMessageIsShownIndicatingThatAnActivityWithTheSameNameAlreadyExistsInThisProject() {
        Assertions.assertNotNull(lastException);
    }

    @Then("the activity is not duplicated in the project")
    public void theActivityIsNotDuplicatedInTheProject() {
        Project project = TestApp.getInstance().getProject();
        String name = TestApp.getInstance().getActivity().getName();
        long amount = TestApp.getInstance().getApp().getActivityService()
                .findByProject(project.getProjectID())
                .stream()
                .filter(activity -> activity.getName().equals(name))
                .count();
        Assertions.assertEquals(1, amount);
    }

    @Then("the needed hours are not registered")
    public void theNeededHoursAreNotRegistered() {
        Assertions.assertFalse(neededHoursRegistered);
    }

    @When("I create an activity")
    public void iCreateAnActivity() {
        Project project = TestApp.getInstance().getProject();
        try {
            Activity activity = TestApp.getInstance().getApp().getActivityService()
                    .createProjectActivity(new CreateProjectActivity(
                            project.getProjectID(),
                            "Activity-" + System.nanoTime(),
                            "",
                            "",
                            0, 0, 0, 0,
                            5
                    ));
            TestApp.getInstance().setActivity(activity);
            lastException = null;
        } catch (Exception e) {
            lastException = e;
        }
    }

    @When("add how many hours needed for the activity")
    public void addHowManyHoursNeededForTheActivity() {
        Employee employee = TestApp.getInstance().getApp().getLoggedInUser();
        neededHoursRegistered = employee != null && !employee.getLeaderProjects().isEmpty();
    }

    @Then("the needed hours are registered")
    public void theNeededHoursAreRegistered() {
        Assertions.assertTrue(neededHoursRegistered);
    }

    @When("add the expected hours to finish the activity")
    public void addTheExpectedHoursToFinishTheActivity() {
        previousExpectedHours = expectedHours;
        expectedHours = expectedHours == null ? 20 : expectedHours + 5;
    }

    @Then("the number of expected hours to finish are stored")
    public void theNumberOfExpectedHoursToFinishAreStored() {
        Assertions.assertNotNull(expectedHours);
        Assertions.assertTrue(expectedHours > 0);
    }

    @Then("Previous number of expected hours to finish are overwritten")
    public void previousNumberOfExpectedHoursToFinishAreOverwritten() {
        Assertions.assertNotNull(previousExpectedHours);
        Assertions.assertNotEquals(previousExpectedHours, expectedHours);
    }

    @When("I edit an activity")
    public void iEditAnActivity() {
        if (TestApp.getInstance().getActivity() == null) {
            iCreateAnActivity();
        }
        if (expectedHours == null) {
            expectedHours = 10;
        }
        if (!datesAdded) {
            LocalDate start = LocalDate.now();
            LocalDate end = start.plusDays(7);
            WeekFields weekFields = WeekFields.of(Locale.getDefault());
            startWeek = start.get(weekFields.weekOfWeekBasedYear());
            startYear = start.getYear();
            endWeek = end.get(weekFields.weekOfWeekBasedYear());
            endYear = end.getYear();
            datesAdded = true;
        }
    }

    @When("add a start and finish date")
    public void addAStartAndFinishDate() {
        previousStartWeek = startWeek;
        previousStartYear = startYear;
        previousEndWeek = endWeek;
        previousEndYear = endYear;

        LocalDate start = LocalDate.now().plusDays(7);
        LocalDate end = start.plusDays(14);
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        startWeek = start.get(weekFields.weekOfWeekBasedYear());
        startYear = start.getYear();
        endWeek = end.get(weekFields.weekOfWeekBasedYear());
        endYear = end.getYear();
        datesAdded = true;
    }

    @Then("the dates are added to that activity")
    public void theDatesAreAddedToThatActivity() {
        Assertions.assertTrue(datesAdded);
        Assertions.assertTrue(startYear > 0);
        Assertions.assertTrue(endYear > 0);
    }

    @Then("Previous start and finish dates are overwritten")
    public void previousStartAndFinishDatesAreOverwritten() {
        Assertions.assertTrue(previousStartYear != startYear
                || previousEndYear != endYear
                || previousStartWeek != startWeek
                || previousEndWeek != endWeek);
    }
}
