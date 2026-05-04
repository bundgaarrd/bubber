package appLogic;

import appLogic.activity.command.CreateProjectActivity;
import appLogic.activity.exception.InvalidHoursException;
import appLogic.employee.Employee;
import appLogic.project.Project;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class RegisterWorkSteps {
    private String errmsg;
    Activity activity;
    private final List<TimeEntry> hoursListed = new ArrayList<>();

    @And("There is an activity named {string}  in this project ")
    public void activityExists(String name){
        Activity checkActivity = null;
        for(Project project : TestApp.getInstance().getProjectRegistry().getAllProjects()){
            try {
                checkActivity = TestApp.getInstance().getApp().getActivityService()
                        .findByProjectAndName(UUID.fromString(project.getProjectID()), name);
                break;
            } catch (Exception ignored) {
            }
        }

        if (checkActivity == null) {
            Project project = TestApp.getInstance().getProject();
            checkActivity = TestApp.getInstance().getApp().getActivityService().createProjectActivity(
                    new CreateProjectActivity(UUID.fromString(project.getProjectID()), name, "", "", LocalDate.now()));
        }

        activity = checkActivity;
        assertNotNull(checkActivity);
    }

    @And("I am assigned to the activity named {string} ")
    public void assignedActivity(String name){
        Employee emp = TestApp.getInstance().getApp().getLoggedInUser();
        TestApp.getInstance().getApp().getActivityService().assignEmployee(activity.getId(), emp);
    }

    @When("I log that I have worked 5 hours")
    public void logHoursWorked(){
        Employee emp = TestApp.getInstance().getApp().getLoggedInUser();
        TimeEntry hoursEntry = TestApp.getInstance().getApp().getActivityService()
                .registerWork(activity.getId(), emp, LocalDateTime.now(), 5);
        hoursListed.add(hoursEntry);
    }

    @Then("5 working hours are registered")
    public void hoursRegistered(){
        assertEquals(5, hoursListed.get(0).getHoursWorked());
    }

    @When("I register negative hours")
    public void negativeHoursRegister(){
        Employee emp = TestApp.getInstance().getApp().getLoggedInUser();
        try {
            TestApp.getInstance().getApp().getActivityService().registerWork(activity.getId(), emp, LocalDateTime.now(), -5);
        } catch (InvalidHoursException e) {
            errmsg = "Invalid hours";
        }
    }

    @Then("an error message is sent")
    public void errormessageSent(){
        assertNotNull(errmsg);
        assertFalse(errmsg.isEmpty());
    }
}
