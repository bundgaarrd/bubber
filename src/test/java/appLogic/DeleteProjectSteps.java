package appLogic;

import io.cucumber.java.en.*;
import org.junit.jupiter.api.Assertions;

public class DeleteProjectSteps {

    private String errorMessage;
    private Project project;

    // Scenario: Deleting an existing project

    @Given("there is a project named {string}")
    public void thereIsAProjectNamed(String name) {
        App app = TestApp.getInstance().getApp();

        project = app.getProjectByName(name);
        if (project == null) {
            project = app.createProject(name);
        }

        Assertions.assertNotNull(project, "Expected project to exist");
        TestApp.getInstance().setProject(project);
    }

    @When("I delete the project named {string}")
    public void iDeleteTheProjectNamed(String name) {
        App app = TestApp.getInstance().getApp();

        Project found = app.getProjectByName(name);
        Assertions.assertNull(found);

        Project project = app.getProjectByName(name);

        try {
            if (project == null) {
                throw new IllegalStateException("Project does not exist");
            }

            app.deleteProject(project.getProjectID());
            TestApp.getInstance().setProject(null);

        } catch (Exception e) {
            errorMessage = e.getMessage();
        }
    }

    @Then("the project no longer exists")
    public void theProjectNoLongerExists() {
        Project project = TestApp.getInstance().getProject();
        Assertions.assertNull(project, "Expected project to be deleted, but it still exists");
    }

    // Scenario: Deleting a non-existing project

    @Given("there is no project named {string}")
    public void thereIsNoProjectNamed(String name) {
        App app = TestApp.getInstance().getApp();

        Project project = app.getProjectByName(name);
        if (project != null) {
            errorMessage = "Project already exists";
            Assertions.fail(errorMessage);
        }
    }

    @Then("an error message is shown indicating that the project does not exist")
    public void errorMessageShown() {
        Assertions.assertNotNull(errorMessage, "Expected an error message but none was thrown");
    }
}