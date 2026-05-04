package appLogic;

import appLogic.activity.command.CreateProjectActivity;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;

import java.time.LocalDate;
import java.util.UUID;

public class CreateActivitySteps {
    private Exception lastException;

    @When("I create an activity named {string}")
    public void iCreateAnActivityNamed(String name) {
        var project = TestApp.getInstance().getProject();
        try {
            Activity activity = TestApp.getInstance().getApp().getActivityService()
                    .createProjectActivity(new CreateProjectActivity(
                            UUID.fromString(project.getProjectID()),
                            name,
                            "",
                            "",
                            LocalDate.now()
                    ));
            TestApp.getInstance().setActivity(activity);
            lastException = null;
        } catch (Exception e) {
            lastException = e;
        }
    }

    @Then("the activity now exists in the project")
    public void theActivityNowExistsInTheProject() {
        var project = TestApp.getInstance().getProject();
        Activity activity = TestApp.getInstance().getApp().getActivityService()
                .findByProjectAndName(UUID.fromString(project.getProjectID()), TestApp.getInstance().getActivity().getName());
        Assertions.assertNotNull(activity);
    }

    @Given("There is already an activity named {string} in this project")
    public void thereIsAlreadyAnActivityNamedInThisProject(String name) {
        var project = TestApp.getInstance().getProject();
        Activity activity = TestApp.getInstance().getApp().getActivityService()
                .createProjectActivity(new CreateProjectActivity(
                        UUID.fromString(project.getProjectID()),
                        name,
                        "",
                        "",
                        LocalDate.now()
                ));
        TestApp.getInstance().setActivity(activity);
    }

    @Then("an error message is shown indicating that an activity with the same name already exists in this project")
    public void anErrorMessageIsShownIndicatingThatAnActivityWithTheSameNameAlreadyExistsInThisProject() {
        Assertions.assertNotNull(lastException);
    }

    @Then("the activity is not duplicated in the project")
    public void theActivityIsNotDuplicatedInTheProject() {
        var project = TestApp.getInstance().getProject();
        String name = TestApp.getInstance().getActivity().getName();
        long amount = TestApp.getInstance().getApp().getActivityService()
                .findByProject(UUID.fromString(project.getProjectID()))
                .stream()
                .filter(activity -> activity.getName().equals(name))
                .count();
        Assertions.assertEquals(1, amount);
    }

    @Then("the needed hours are not registered")
    public void theNeededHoursAreNotRegistered() {
        throw new io.cucumber.java.PendingException();
    }

    @When("I create an activity")
    public void iCreateAnActivity() {
        throw new io.cucumber.java.PendingException();
    }

    @When("add how many hours needed for the activity")
    public void addHowManyHoursNeededForTheActivity() {
        throw new io.cucumber.java.PendingException();
    }

    @Then("the needed hours are registered")
    public void theNeededHoursAreRegistered() {
        throw new io.cucumber.java.PendingException();
    }

    @When("add the expected hours to finish the activity")
    public void addTheExpectedHoursToFinishTheActivity() {
        throw new io.cucumber.java.PendingException();
    }

    @Then("the number of expected hours to finish are stored")
    public void theNumberOfExpectedHoursToFinishAreStored() {
        throw new io.cucumber.java.PendingException();
    }

    @Then("Previous number of expected hours to finish are overwritten")
    public void previousNumberOfExpectedHoursToFinishAreOverwritten() {
        throw new io.cucumber.java.PendingException();
    }

    @When("I edit an activity")
    public void iEditAnActivity() {
        throw new io.cucumber.java.PendingException();
    }

    @When("add a start and finish date")
    public void addAStartAndFinishDate() {
        throw new io.cucumber.java.PendingException();
    }

    @Then("the dates are added to that activity")
    public void theDatesAreAddedToThatActivity() {
        throw new io.cucumber.java.PendingException();
    }

    @Then("Previous start and finish dates are overwritten")
    public void previousStartAndFinishDatesAreOverwritten() {
        throw new io.cucumber.java.PendingException();
    }
}
