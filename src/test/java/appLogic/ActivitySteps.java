package appLogic;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;


public class ActivitySteps {
/*

    @Given("I am logged in as project leader or employee")
    public void iAmLoggedInAsProjectLeaderOrEmployee(){
        boolean admin = TestApp.getInstance().getApp().isAdminLoggedIn();
        Assertions.assertTrue(admin, "User is logged in as a projectleader or employee");
    }


    @And("There is no activity named {string} in this project ")
    public void activityNotExisting(String name){
        Activity activity = TestApp.getInstance().getProject().getActivity(name);
        Assertions.assertNull(activity, "Expected no activity named " + name + " to exist in the project, but it does.");
     }
    

    @When("I create an activity named {string}")
    public void createActivityAnalysis(String name){
        ProjectActivity newActivity = new ProjectActivity(name);
        TestApp.getInstance().getProject().addActivity(newActivity);
        TestApp.getInstance().setActivity(newActivity);
    }

    @Then("the activity now exists in the project")
    public void activityExists(){
        Activity activity = TestApp.getInstance().getActivity();
        Assertions.assertNotNull(activity, "Expected the activity to exist in the project, but it does not.");
    }

    // creating preexisting activity

    @Given("I am logged in as project leader or employee")

    @And("And there is already an activity named {string} in this project")
    public void activityAlreadyExisting(String name){
        Activity activity = TestApp.getInstance().getProject().getActivity(name);
        Assertions.assertNotNull(activity, "Expected an activity named " + name + " to exist in the project, but it does not.");
    }*/
}
