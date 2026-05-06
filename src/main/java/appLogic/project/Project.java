package appLogic.project;

import appLogic.Activity;
import appLogic.Customer;
import appLogic.Report;
import appLogic.TimeEntry;
import appLogic.employee.Employee;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class Project {
    private String projectID;
    private String projectName;
    private boolean hasCustomer;
    private Employee projectLeader;
    private boolean isCompleted = false;
    private Set<Customer> customerList;
    private int expectedHours;
    private Set<TimeEntry> events;
    private Set<Employee> employees;

    
    public Project(String projectID, String projectName) {
        this.projectID = projectID;
        this.projectName = projectName;
        this.employees = new HashSet<>(); 
        this.events = new HashSet<>();
        this.customerList = new HashSet<>();
    }

    // Status true
    public void setCompletedStatus(boolean status) {
        this.isCompleted = status;
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


    public boolean assignEmployee(Employee empl) {
        boolean b;
        if (employees.contains(empl)) {
            b = false;
        } else {
            employees.add(empl);
            b = true;
        }
        return b;
    }

    public void setExpectedHours(int hours) {
        this.expectedHours = hours;
    }

    public Set<TimeEntry> getEvents() {
        Set<TimeEntry> entries = new HashSet<>();
        return entries;
    }

    public Set<Customer> getCustomers() {
        return null;
    }

    public String getProjectID() {
        return projectID;
    }

    public String getProjectName() {
        return projectName;
    }

    public int getExpectedHours() {
        return expectedHours;
    }

}
