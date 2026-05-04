package appLogic;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Locale;

import javax.annotation.processing.Generated;

import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;


public class FixedActivitySteps {/*
    private String ErrorMessage;
    private boolean addedActivity;
    private FixedActivity fixedActivity;
    private boolean checkDates;
    private Timesheet timesheet = TestApp.getInstance().getApp().getTimesheet();

    @Given("I am logged in as project leader or employee")
    public void iAmLoggedInAsProjectLeaderOrEmployee(){
        boolean admin = TestApp.getInstance().getApp().isAdminLoggedIn();
        assertTrue(admin, "User is logged in as a projectleader or employee");
    }  

        
    @And("There is no other fixed activity on the same dates")
    public void fixedActivityOtherDates(){
 
        assertFalse(checkDates, "Already existing a fixed activity at that date");   
        }
    
    @When("I add a fixed activity with name {string} and specified start and end date")
    public void addFixedActivity(String name){
        fixedActivity = new FixedActivity(name);


        Localdate startDate = fixedActivity.getStartDate();
        WeekFields weekfields = weekFields.of(Locale.getDefault());
        int week = startDate.get(weekFields.weekOfWeekBasedYear());
        int year = startDate.getYear();
        checkDates = fixedActivity.isOverlappingWeek(week, year);

        if(!checkDates){
        addedActivity = timesheet.addFixedActivity(fixedActivity);
        }
    }
    @Then("the fixed activity is added to the timesheet")
    public void addedActivityTimesheet(){
        assertTrue(addedActivity);
    }
    
    
    


    @And(" I have a fixed activity called {string}  with specified start and end date")
    public void addCourseActivity(String name){
        FixedActivity currentActivity = new FixedActivity(name);
        timesheet.addFixedActivity(currentActivity);  
    }

    @Then(" an error message is shown indicating that there is already a fixed activity on the same dates")
    public void setErrorMessage(){
    if(checkDates){
        ErrorMessage = ("ERROR: There already exists a fixed activity at that date");
    }
    assertEquals("ERROR  There already exists a fixed activity at that date", ErrorMessage);
}

    @And("The timesheet is not updated")
    public void timesheetNotUpdated(){
        assertFalse(addedActivity);
    }


*/
}
