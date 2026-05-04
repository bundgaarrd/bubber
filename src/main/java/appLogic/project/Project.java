package appLogic.project;

import appLogic.App;
import appLogic.Customer;
import appLogic.Report;
import appLogic.TimeEntry;
import appLogic.employee.Employee;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Project {

    private UUID projectUUID;
    private String projectID;
    private String name;
    private boolean hasCustomer;
    private Employee projectLeader;
    private boolean isCompleted = false;
    private Set<Customer> customerList;
    private int expectedHours;
    private Report currentReport;
    private Set<TimeEntry> events;
    private Set<Employee> employees;

    // Constructor -- Use this instead of createProject?
    public Project(String name) {
        this.name = name;

        // Use ProjectIdGenerator instead of UUID. This is temporary.
        this.projectUUID = UUID.randomUUID();
        this.projectID = projectUUID.toString();
        //TODO: Default huba project leader?
        assignProjectLeader(App.getInstance().getEmployeeRepository().findByInitials("huba"));
    }

    // Status true
    public void setCompletedStatus(boolean status) {
        this.isCompleted = status;
    }

    public Employee getProjectLeader() {
        return projectLeader;
    }

    public void assignProjectLeader(Employee empl) {
        if(projectLeader != null) projectLeader.removeProjectAsLeader(this);
        this.projectLeader = empl;
        empl.addProjectAsLeader(this);
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

    public String getProjectID() {
        return projectID;
    }

    public String getProjectName() {
        return name;
    }

}
