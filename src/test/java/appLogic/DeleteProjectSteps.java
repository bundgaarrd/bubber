package appLogic;

import appLogic.project.Project;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;


// Feature: Deleting projects

public class DeleteProjectSteps {
    private String projectName;
    private Exception exception;

    @When("I delete the project with name {string}")
    public void iDeleteTheProject(String name) {
        projectName = name;
        try {
            TestApp.getInstance().getProjectRegistry().deleteProjectByName(name);
        } catch (Exception e) {
            exception = e;
        }
    }

    @Then("the project no longer exists in the system")
    public void theProjectNoLongerExistsInTheSystem() {
        Project project = TestApp.getInstance().getProjectRegistry().getProjectByName(projectName);
        Assertions.assertNull(project, "Expected the project to be deleted, but it still exists in the system.");
    }

    @Then("An error message is shown indicating that the project does not exist in the system")
    public void anErrorMessageIsShownIndicatingThatTheProjectDoesNotExistInTheSystem() {
        Assertions.assertNotNull(exception, "Expected an error message to be shown indicating that the project does not exist in the system, but no exception was thrown.");
    }
}