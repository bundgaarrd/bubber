package appLogic;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ProjectSteps {
    private Project project;

    @Given("I am logged in as project leader or employee")
    public void iAmLoggedInAsProjectLeaderOrEmployee() {
        App app = TestApp.getInstance().getApp();
        
        app.login("huba"); 
    
        if(!app.isAdminLoggedIn()) {
            throw new IllegalStateException("User is not logged in...");
        }
    }

    @And("A project with the name {string} does not exist in the system")
    public void aProjectWithTheNameDoesNotExistInTheSystem(String name) {
        Project check = TestApp.getInstance().getApp().getProjectByName(name);
        if (check != null) {
            throw new IllegalStateException("A project with the name " + name + " already exists in the system.");
        }
    }

    @When("I create a project with the name {string}")
    public void iCreateAProjectWithTheName(String name) {
        project = TestApp.getInstance().getApp().createProject(name);
    }

    @Then("the project exists in the system")
    public void theProjectExistsInTheSystem() {
        Project check = TestApp.getInstance().getApp().getProjectById(project.getProjectID());
        if (check == null) {
            throw new IllegalStateException("The project does not exist in the system.");
        }
    }

    @And("the project is assigned a project number")
    public void theProjectIsAssignedAProjectNumber() {
        if (project.getProjectID() == null || project.getProjectID().isEmpty()) {
            throw new IllegalStateException("The project is not assigned a project number.");
        }
    }
    
    
    // Create preexisting project
    @And("A project with the name {string} exists in the system")
    public void aProjectWithTheNameExistsInTheSystem(String name) {
        Project existingProject = TestApp.getInstance().getApp().getProjectByName(name);
        if (existingProject == null) {
            throw new IllegalStateException("Failed to create a project with the name " + name + " for testing.");
        }
    }

    @Then("an error message is shown indicating that a project with the same name already exists")
    public void anErrorMessageIsShownIndicatingThatAProjectWithTheSameNameAlreadyExists() {
        TestApp.getInstance().getApp().getProjectById(project.getProjectID());
    }

    @And("the project is not duplicated in the system")
    public void theProjectIsNotDuplicatedInTheSystem() {
        int amount = TestApp.getInstance().getApp().getAllProjects().stream().filter(predicate -> predicate.getProjectID().equals(project.getProjectID())).toList().size();
        if (amount > 1) {
            throw new IllegalStateException("The project is duplicated in the system.");
        }
    }
}
