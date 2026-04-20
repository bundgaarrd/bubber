package appLogic;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class DeleteProjectSteps {
    
// Scenario: Deleting an existing project

@Given("I am logged in as project leader or employee")
public void LoggedIn1() {
    boolean admin = TestApp.getInstance().getApp().isAdminLoggedIn();
    if(!admin) {
        throw new IllegalStateException("User is not logged in as project leader or employee.");
    }
}

@Given("A project named {string} exists")
public void ProjectExists(String string) {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
}
@When("I delete the project named {string}")
public void DeleteProjectNamed(String string) {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
}
@Then("The project no longer exists in the system")
public void theProjectNoLongerExists() {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
}


// Scenario: Deleting a non-existing project from the system

@Given("I am logged in as project leader or employee")
public void LoggedIn2() {
    boolean admin = TestApp.getInstance().getApp().isAdminLoggedIn();
    if(!admin) {
        throw new IllegalStateException("User is not logged in as project leader or employee.");
    }
}

@Given("There is no project named {string} in the system")
public void ProjectDoesNotExist(String string) {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
}
@When("I delete the project named {string}")
public void DeleteProject(String string) {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
}
@Then("an error message is shown indicating that the project does not exist in the system")
public void ErrorMessageSaysProjectDoesNotExist() {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
}
}
