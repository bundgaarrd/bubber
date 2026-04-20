package appLogic;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class AssignEmploySteps {

// Scenario: Assigning an available employee to an activity

@Given("I am logged in as project leader or employee")
public void LoggedIn1() {
    boolean admin = TestApp.getInstance().getApp().isAdminLoggedIn();
    if(!admin) {
        throw new IllegalStateException("User is not logged in as project leader or employee.");
    }
}

@Given("The selected employee is available")
public void SelectedEmployeeAvailable() {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
}

@When("Selected employee is assigned to the activity")
public void AssignEmployeeToActivity() {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
}
@Then("Selected employee is added to the activity")
public void AddEmployeeToActivity() {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
}

@Then("Employee schedule is updated")
public void UpdateEmployeeSchedule() {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
}

@Given("I am logged in as project leader or employee")
public void LoggedIn2() {
    boolean admin = TestApp.getInstance().getApp().isAdminLoggedIn();
    if(!admin) {
        throw new IllegalStateException("User is not logged in as project leader or employee.");
    }
}


// Scenario: Assigning a non-available employee to an activity

@Given("The selected employee is unavailable")
public void SelectedEmployeeUnavailable() {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
}

@When("Selected employee is assigned to the activity")
public void AssignUnavailableEmployee() {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
}
@Then("Selected employee is not added to the activity")
public void DoNotAddEmployeeToActivity() {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
}

@Then("Error message is shown indicating that the employee is unavailable for the activity")
public void ShowUnavailableEmployeeError() {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
}

}
