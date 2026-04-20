package appLogic;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class AssignLeadSteps {

// Scenario: Assigning an employee as project leader

@Given("I am logged in as project leader or employee")
public void LoggedIn1() {
    boolean admin = TestApp.getInstance().getApp().isAdminLoggedIn();
    if(!admin) {
        throw new IllegalStateException("User is not logged in as project leader or employee.");
    }
}

@Given("There is a project named {string} with no assigned project leader")
public void ProjectExists(String projectName) {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
}

@When("An employee is chosen as project leader for the project")
public void EmployeeChosenAsLeader(String projectName) {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
}
@When("Selected employee confirms role as project leader")
public void EmployeeConfirmsRole() {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
}

@Then("Employee is assigned as project leader for the project")
public void AssignEmployeeAsLeader() {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
}


// Scenario: Employee declines being project leader

@Given("I am logged in as project leader or employee")
public void LoggedIn2() {
    boolean admin = TestApp.getInstance().getApp().isAdminLoggedIn();
    if(!admin) {
        throw new IllegalStateException("User is not logged in as project leader or employee.");
    }
}

@Given("There is a project named {string} with no assigned project leader")
public void ProjectExistsNoLeader(String projectName) {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
}

@When("An employee is chosen as project leader for the project")
public void EmployeeChosenAsLead(String projectName) {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
}

@When("Selected employee does not confirm role as project leader")
public void EmployeeDeclinesRole() {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
}

@Then("Selected employee is not assigned as project leader and error message is shown")
public void UnableToAssignLeader() {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
}

}
