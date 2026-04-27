package appLogic;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;

/**
 * Scenario: Deleting an existing activity from a project
 */
public class DeleteActivitySteps {
    String errorMessage;

    @Given("There is an activity named {string} in this project")
    public void thereIsAnActivityNamedInThisProject(String name) {
        Project project = TestApp.getInstance().getProject();
        Activity activity = project.getActivity(name);
        Assertions.assertNotNull(activity, "Expected an activity named " + name + " to exist in the project, but it does not.");
    }

    @When("I delete the activity named {string}")
    public void iDeleteTheActivityNamed(String name) {
        Project project = TestApp.getInstance().getProject();
        project.deleteActivity(name);
        TestApp.getInstance().setActivity(null);
    }

    @Then("the activity no longer exists in the project")
    public void theActivityNoLongerExistsInTheProject() {
        Activity activity = TestApp.getInstance().getActivity();
        Assertions.assertNull(activity, "Expected the activity to be deleted, but it still exists.");
    }

    @Given("There is no activity named {string} in this project")
    public void thereIsNoActivityNamedInThisProject(String name) {
        Project project = TestApp.getInstance().getApp().getProjectByName(name);
        if (project != null) {
            errorMessage = "There is already an activity named " + name + " in the project.";
            Assertions.fail(errorMessage);
        }
    }

    @Then("an error message is shown indicating that the activity does not exist in the project")
    public void anErrorMessageIsShownIndicatingThatTheActivityDoesNotExistInTheProject() {
        Assertions.assertNotNull(errorMessage, "Expected an error message but none was thrown.");
    }
}