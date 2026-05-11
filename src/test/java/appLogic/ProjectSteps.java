package appLogic; // Lavet af Valdemar s246575

import appLogic.project.Project;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;

public class ProjectSteps {
    private Exception exception;

    @And("A project with the name {string} does not exist in the system")
    public void aProjectWithTheNameDoesNotExistInTheSystem(String name) {
        Project check = TestApp.getInstance().getProjectRegistry().getProjectByName(name);
        Assertions.assertNull(check, "A project with the name " + name + " already exists in the system.");
    }

    @When("I create a project with the name {string}")
    public void iCreateAProjectWithTheName(String name) {
        try {
            Project newProject = TestApp.getInstance().getProjectRegistry().createProject(name);
            TestApp.getInstance().setProject(newProject);
        } catch (Exception e) {
            exception = e;
        }
    }

    @Then("the project exists in the system")
    public void theProjectExistsInTheSystem() {
        Project current = TestApp.getInstance().getProject();
        Project check = TestApp.getInstance().getProjectRegistry().getProjectById(current.getProjectID());
        Assertions.assertNotNull(check, "The project does not exist in the system.");
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
        Project existingProject = TestApp.getInstance().getProjectRegistry().getProjectByName(name);
        TestApp.getInstance().setProject(existingProject);
        Assertions.assertNotNull(existingProject, "A project with the name " + name + " does not exist in the system.");
    }

    @Then("an error message is shown indicating that a project with the same name already exists")
    public void anErrorMessageIsShownIndicatingThatAProjectWithTheSameNameAlreadyExists() {
        Assertions.assertNotNull(exception, "Expected an error message to be shown indicating that a project with the same name already exists, but no exception was thrown.");
    }

    @And("the project is not duplicated in the system")
    public void theProjectIsNotDuplicatedInTheSystem() {
        Project project = TestApp.getInstance().getProject();
        int amount = TestApp.getInstance().getProjectRegistry().getAllProjects().stream().filter(predicate -> predicate.getProjectID().equals(project.getProjectID())).toList().size();
        Assertions.assertFalse(amount > 1, "The project is duplicated in the system.");
    }
}