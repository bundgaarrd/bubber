package appLogic;

import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ProjectSteps {
    @Given("I am logged in as project leader or employee")
    public void iAmLoggedInAsProjectLeaderOrEmployee() {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @And("A project with the name {string} does not exist in the system")
    public void aProjectWithTheNameDoesNotExistInTheSystem(String arg0) {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("I create a project with the name {string}")
    public void iCreateAProjectWithTheName(String arg0) {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("the project exists in the system")
    public void theProjectExistsInTheSystem() {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @And("the project is assigned a project number")
    public void theProjectIsAssignedAProjectNumber() {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
    
    
    // Create preexisting project
    @And("A project with the name {string} exists in the system")
    public void aProjectWithTheNameExistsInTheSystem(String arg0) {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("an error message is shown indicating that a project with the same name already exists")
    public void anErrorMessageIsShownIndicatingThatAProjectWithTheSameNameAlreadyExists() {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @And("the project is not duplicated in the system")
    public void theProjectIsNotDuplicatedInTheSystem() {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
}
