package appLogic;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;

public class ActivitySteps{

    @And("There is no activity named {string} in this project")
    public void activityNotExisting(String name) {
        Project project = TestApp.getInstance().getProject();
        Activity checkActivity = project.getActivity(name);
        Assertions.assertNull(checkActivity,
                "Expected no activity named " + name + " but one already exists");
    }

    @And("There is already an activity named {string} in this project")
    public void activityAlreadyExisting(String name) {
        Project project = TestApp.getInstance().getProject();
        Activity existingActivity = project.getActivity(name);
        Assertions.assertNotNull(existingActivity,
                "Expected an activity named " + name + " to exist but none was found");
        TestApp.getInstance().setActivity(existingActivity);
    }

    @When("I create an activity named {string}")
    public void createActivity(String name) {
        Project project = TestApp.getInstance().getProject();
        Activity newActivity = project.createActivity(name);
        TestApp.getInstance().setActivity(newActivity);
    }

    @Then("the activity now exists in the project")
    public void activityExists(){
        assertTrue(activity != null);
    }

    // creating preexisting activity

    @Given("I am logged in as project leader or employee")

    @And("And there is already an activity named {string} in this project")
    public void activityAlreadyExisting(String name){
        existingActivity = TestApp.getInstance.getActivity(name);
        

    }

}
