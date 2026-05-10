package appLogic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;


public class FixedActivitySteps {
    private String ErrorMessage;
    private boolean addedActivity;
    private FixedActivity fixedActivity;
    private boolean checkDates;
    private List<FixedActivity> fixedacts = new ArrayList<>();

        
    @And("There is no other fixed activity on the same dates")
    public void fixedActivityOtherDates(){
 
        assertFalse(checkDates, "Already existing a fixed activity at that date");
        }
    
    @When("I add a fixed activity with name {string} and specified start and end date")
    public void addFixedActivity(String name){
        
        


    
        LocalDate startDate = LocalDate.of(2024,3,4);
        LocalDate endDate = LocalDate.of(2024, 3,8);
        WeekFields weekfields = WeekFields.of(Locale.getDefault());
        int week = startDate.get(weekfields.weekOfWeekBasedYear());
        int year = startDate.getYear();
        fixedActivity = new FixedActivity(name, "Description of the test", "Summary of the Test", startDate, endDate, FixedActivityType.COURSE);
        checkDates = fixedActivity.isOverlappingWeek(week, year);
        assert week > 0 && week <= 52; //precondition uger skal være størrre end 0 og mindre end 52

        if(!checkDates){
        fixedacts.add(fixedActivity);
        addedActivity = true;
        }
    }
    @Then("the fixed activity is added to the timesheet")
    public void addedActivityTimesheet(){
        assertTrue(addedActivity); // postcondition med en assertTrue 
    }
    
    
    


    @And(" I have a fixed activity called {string}  with specified start and end date")
    public void addCourseActivity(String name){
        LocalDate startDate = LocalDate.of(2024,3,4);
        LocalDate endDate = LocalDate.of(2024, 3,8);

        FixedActivity currentActivity = new FixedActivity(name, "Description of the test", "Summary of the Test", startDate, endDate, FixedActivityType.COURSE);
         WeekFields weekfields = WeekFields.of(Locale.getDefault());
        int week = startDate.get(weekfields.weekOfWeekBasedYear());
        int year = startDate.getYear();
        fixedActivity = new FixedActivity(name, "Description of the test", "Summary of the Test", startDate, endDate, FixedActivityType.COURSE);
        checkDates = currentActivity.isOverlappingWeek(week, year);
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



}
