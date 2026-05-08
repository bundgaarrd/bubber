package appLogic;

import appLogic.activity.command.CreateProjectActivity;
import appLogic.activity.exception.ActivityNotFoundException;
import appLogic.activity.exception.DuplicateActivityException;
import appLogic.activity.impl.Activity;
import appLogic.project.Project;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;

import java.time.LocalDate;

public class DeleteActivitySteps {
    private String errorMessage;

    @Given("There is an activity named {string} in this project")
    public void thereIsAnActivityNamedInThisProject(String name) {
        Project project = TestApp.getInstance().getProject();
        Activity activity;
        try {
            activity = TestApp.getInstance().getApp().getActivityService()
                    .createProjectActivity(new CreateProjectActivity(
                            project.getProjectID(), name, "", "", LocalDate.now()
                    ));
        } catch (DuplicateActivityException e) {
            activity = TestApp.getInstance().getApp().getActivityService().findByProjectAndName(
                    project.getProjectID(), name
            );
        }
        TestApp.getInstance().setActivity(activity);
        Assertions.assertNotNull(activity);
    }

    @When("I delete the activity named {string}")
    public void iDeleteTheActivityNamed(String name) {
        var project = TestApp.getInstance().getProject();
        try {
            TestApp.getInstance().getApp().getActivityService().deleteActivity(project.getProjectID(), name);
            TestApp.getInstance().setActivity(null);
        } catch (Exception e) {
            errorMessage = "An error occurred while trying to delete the activity: " + e.getMessage();
        }
    }

    @Then("the activity no longer exists in the project")
    public void theActivityNoLongerExistsInTheProject() {
        Assertions.assertNull(TestApp.getInstance().getActivity());
    }

    @Given("There is no activity named {string} in this project")
    public void thereIsNoActivityNamedInThisProject(String name) {
        var project = TestApp.getInstance().getProject();
        try {
            TestApp.getInstance().getApp().getActivityService().findByProjectAndName(project.getProjectID(), name);
            Assertions.fail("There is already an activity named " + name + " in the project.");
        } catch (ActivityNotFoundException ignored) {
        }
    }

    @Then("an error message is shown indicating that the activity does not exist in the project")
    public void anErrorMessageIsShownIndicatingThatTheActivityDoesNotExistInTheProject() {
        Assertions.assertNotNull(errorMessage);
    }
}
