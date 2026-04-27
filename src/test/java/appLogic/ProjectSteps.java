package appLogic;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;

public class ProjectSteps {
    @Given("I am logged in as project leader or employee")
    public void iAmLoggedInAsProjectLeaderOrEmployee() {
        TestApp.getInstance().getApp().setAdminLoggedIn(true);
    }

    @And("A project with the name {string} does not exist in the system")
    public void aProjectWithTheNameDoesNotExistInTheSystem(String name) {
        Project check = TestApp.getInstance().getApp().getProjectByName(name);
        Assertions.assertNull(check, "A project with the name " + name + " already exists in the system.");
    }

    @When("I create a project with the name {string}")
    public void iCreateAProjectWithTheName(String name) {
        TestApp.getInstance().setProject(TestApp.getInstance().getApp().createProject(name));
    }

    @Then("the project exists in the system")
    public void theProjectExistsInTheSystem() {
        Project current = TestApp.getInstance().getProject();
        Project check = TestApp.getInstance().getApp().getProjectById(current.getProjectID());
        if (check == null) {
            throw new IllegalStateException("The project does not exist in the system.");
        }
    }

    @And("the project is assigned a project number")
    public void theProjectIsAssignedAProjectNumber() {
        Project project = TestApp.getInstance().getProject();
        Assertions.assertNotNull(project.getProjectID(), "The project is not assigned a project number.");
        Assertions.assertFalse(project.getProjectID().isEmpty(), "The project is not assigned a project number.");
    }
    
    
    // Create preexisting project
    @And("A project with the name {string} exists in the system")
    public void aProjectWithTheNameExistsInTheSystem(String name) {
        Project existingProject = TestApp.getInstance().getApp().getProjectByName(name);
        Assertions.assertNotNull(existingProject, "A project with the name " + name + " does not exist in the system.");
    }

    @Then("an error message is shown indicating that a project with the same name already exists")
    public void anErrorMessageIsShownIndicatingThatAProjectWithTheSameNameAlreadyExists() {
        Project project = TestApp.getInstance().getProject();
        TestApp.getInstance().getApp().getProjectById(project.getProjectID());
    }

    @And("the project is not duplicated in the system")
    public void theProjectIsNotDuplicatedInTheSystem() {
        Project project = TestApp.getInstance().getProject();
        int amount = TestApp.getInstance().getApp().getAllProjects().stream().filter(predicate -> predicate.getProjectID().equals(project.getProjectID())).toList().size();
        Assertions.assertFalse(amount > 1, "The project is duplicated in the system.");
    }
}
