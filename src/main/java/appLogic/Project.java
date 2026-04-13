package appLogic;

import jdk.javadoc.doclet.Reporter;

import java.util.*;

public class Project {

//    public static void main(String[] args) {
//
//    }

    private String projectID;
    private String name;
    private boolean hasCustomer;
    private Employee projectLeader;
    private Set<Activity> activities;
    private boolean isCompleted = false;
    private Set<Customer> customerList;
    private int expectedHours;
    private Report currentReport;
    private Set<TimeEntry> events;
    private Set<Employee> employees;


    // Constructor -- Use this instead of createProject?
    public Project(String name, Integer expectedHours) {
        this.name = name;
        this.expectedHours = expectedHours;
    }

    // Status true
    public void setCompletedStatus(boolean status) {
        this.isCompleted = status;
    }

    // -----SETTERS-----
    public void addActivity(Activity act) {
        activities.add(act);
    }

    public void assignProjectLeader(Employee empl) {
        this.projectLeader = empl;
    }

    public Report generateReport() {
        return currentReport;
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

}
