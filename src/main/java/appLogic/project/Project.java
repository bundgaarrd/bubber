package appLogic.project;

import appLogic.TimeEntry;
import appLogic.employee.Employee;

import java.util.HashSet;
import java.util.Set;

public class Project {
    private String projectID;
    private String projectName;
    private boolean hasCustomer;
    private Employee projectLeader;
    private double expectedHours;
    private Set<TimeEntry> events;

    public Project(String projectID, String projectName) {
        this.projectID = projectID;
        this.projectName = projectName;
        this.events = new HashSet<>();
    }

    public Employee getProjectLeader() {
        return projectLeader;
    }

    public void assignProjectLeader(Employee empl) {
        if (projectLeader != null) {
            throw new IllegalStateException("Project already has a project leader.");
        }
        this.projectLeader = empl;
        if(empl != null) empl.addProjectAsLeader(this);
    }

    public void setExpectedHours(double hours) {
        this.expectedHours = hours;
    }

    public Set<TimeEntry> getEvents() {
        return events;
    }

    public String getProjectID() {
        return projectID;
    }

    public String getProjectName() {
        return projectName;
    }

    public double getExpectedHours() {
        return expectedHours;
    }

}
