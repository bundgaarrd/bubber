package appLogic;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/**
 * Feature: An activity is deleted from a project
 */
public class CreateActivitySteps {
    @When("I create an activity named {string}")
    public void iCreateAnActivityNamed(String name) {
        Project project = TestApp.getInstance().getProject();
        Activity activity = project.createActivity(name);
        project.addActivity(activity);
        TestApp.getInstance().setActivity(activity);
    }

    @Then("the activity now exists in the project")
    public void theActivityNowExistsInTheProject() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Given("There is already an activity named {string} in this project")
    public void thereIsAlreadyAnActivityNamedInThisProject(String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("an error message is shown indicating that an activity with the same name already exists in this project")
    public void anErrorMessageIsShownIndicatingThatAnActivityWithTheSameNameAlreadyExistsInThisProject() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @Then("the activity is not duplicated in the project")
    public void theActivityIsNotDuplicatedInTheProject() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }


    @Then("the needed hours are not registered")
    public void theNeededHoursAreNotRegistered() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @When("I create an activity")
    public void iCreateAnActivity() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @When("add how many hours needed for the activity")
    public void addHowManyHoursNeededForTheActivity() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @Then("the needed hours are registered")
    public void theNeededHoursAreRegistered() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @When("add the expected hours to finish the activity")
    public void addTheExpectedHoursToFinishTheActivity() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @Then("the number of expected hours to finish are stored")
    public void theNumberOfExpectedHoursToFinishAreStored() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @Then("Previous number of expected hours to finish are overwritten")
    public void previousNumberOfExpectedHoursToFinishAreOverwritten() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @When("I edit an activity")
    public void iEditAnActivity() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @When("add a start and finish date")
    public void addAStartAndFinishDate() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @Then("the dates are added to that activity")
    public void theDatesAreAddedToThatActivity() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @Then("Previous start and finish dates are overwritten")
    public void previousStartAndFinishDatesAreOverwritten() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
}
