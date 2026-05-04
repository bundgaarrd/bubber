package appLogic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import io.cucumber.java.en.And;
import io.cucumber.java.en.When;

import io.cucumber.java.en.Then;
public class RegisterWorkSteps {
    private String errmsg;
    Activity activity;
    private List<TimeEntry> hoursListed = new ArrayList<>();
    

    @And("There is an activity named {string}  in this project ")
    public void ActivityExists(String name){
        Activity checkActivitystring =null;
        for(Activity a :  TestApp.getInstance().getApp().getAllActivities()){
        if(a.getName().equals(name)){
            checkActivitystring=a;
            break;
        }
        activity = checkActivitystring;

        assertTrue(checkActivitystring != null);
        }


    }


    @And("I am assigned to the activity named {string} ")
        public void AssignedActivity(String name){
        Employee Emp = TestApp.getInstance().getApp().getLoggedInUser();
        activity.assignEmployee(Emp);
    }


    @When("I log that i have worked 5 hours")
    public void logHoursWorked(){
        Employee Emp = TestApp.getInstance().getApp().getLoggedInUser();
        TimeEntry hoursEntry = new TimeEntry(Emp , activity, null, 5);
        hoursListed.add(hoursEntry);



    }

    @Then("Then 5 working hours are registered")
    public void hoursRegistered(){
        assertEquals(5, hoursListed.get(0).getHoursWorked());

    }    
    

    @When("I register negative hours ")
    public void negativeHoursRegister(){
         Employee Emp = TestApp.getInstance().getApp().getLoggedInUser();
        TimeEntry negativeEntry = new TimeEntry(Emp , activity, null,-5);
        try{
        if(negativeEntry.getHoursWorked() < 0){
            throw new IllegalArgumentException();
        } 
    }catch (IllegalArgumentException e){
        errmsg = "Invalid hours";
    }

    }

    @Then("an error message is sent")
    public void ErrormessageSent(){
        assertNotNull(errmsg);
        assertTrue(!errmsg.isEmpty());
    }


    

    

    


}
    

