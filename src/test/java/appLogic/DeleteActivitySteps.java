package appLogic;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class DeleteActivitySteps {

// Scenario: Deleting an existant activity from a project

@Given("I am logged in as project leader or employee")
public void LoggedIn1() {
    boolean admin = TestApp.getInstance().getApp().isAdminLoggedIn();
    if(!admin) {
        throw new IllegalStateException("User is not logged in as project leader or employee.");
    }
}

@Given("There is an activity named {string} in this project")
public void thereIsAnActivityNamedInThisProject(String string) {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
}
@When("I delete the activity named {string}")
public void DeleteActivityNamed(String string) {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
}
@Then("the activity no longer exists in the project")
public void theActivityNoLongerExistsInTheProject() {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
}


// Scenario: Deleting a non-existing activity from a project

@Given("I am logged in as project leader or employee")
public void LoggedIn2() {
    boolean admin = TestApp.getInstance().getApp().isAdminLoggedIn();
    if(!admin) {
        throw new IllegalStateException("User is not logged in as project leader or employee.");
    }
}

@Given("There is no activity named {string} in this project")
public void thereIsNoActivityNamedInThisProject(String string) {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
}
@When("I delete the activity named {string}")
public void DeleteActivityNamed1(String string) {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
}
@Then("an error message is shown indicating that the activity does not exist in the project")
public void anErrorMessageIsShownIndicatingThatTheActivityDoesNotExistInTheProject() {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
}
}
