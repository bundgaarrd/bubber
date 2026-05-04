package appLogic;
import javax.annotation.processing.Generated;

import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;


public class ActivitySteps{
    private Project project;
    private Activity activity;


    @Given("I am logged in as project leader or employee")
    public void iAmLoggedInAsProjectLeaderOrEmployee(){
    boolean admin = TestApp.getInstance().getApp().isAdminLoggedIn();
    assertTrue(admin, "User is logged in as a projectleader or employee");
    }  
        

    @And("There is no activity named {string} in this project ")
        public void activityNotExisting(String name){
        Activity checkActivity = TestApp.getInstance().getApp().getActivity(name);
        if (checkActivity = null)    
            assertTrue(false);
        }
    

    @When(" I create an activity named {string}")
    public void createActivityAnalysis(String name){
        newActivity = app.createActivityAnalysis(name);

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
