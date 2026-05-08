package appLogic.activity.impl;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import appLogic.activity.exception.UnavailableEmployeeException;
import appLogic.employee.Employee;

public abstract class Activity {

    private final UUID id;
    private final String name;
    private final String description;
    private final String summary;
    private final LocalDate date;
     private final String projectId;
    private final Set<Employee> assignedEmployees = new HashSet<>();

    public Activity(String name, String description, String summary, LocalDate date, String projectId) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.description = description;
        this.summary = summary;
        this.date = date;
        this.projectId = projectId;
    }

    public String getProjectId() {
        return projectId; 
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getProjectReferenceId() {
        return id.toString();
    }

    public Set<Employee> getAssignedEmployees() {
        return assignedEmployees;
    }

    public boolean assignEmployee(Employee emp) {
        if(!emp.isAvailable()) throw new UnavailableEmployeeException("Employee is unavailable.");
        return assignedEmployees.add(emp);
    }

    public boolean removeEmployee(Employee emp) {
        return assignedEmployees.remove(emp);
    }

    public String getDescription() {
        return description;
    }

    public String getSummary() {
        return summary;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getDuration() {
        return 0;
    }

    public int getHoursExpected() {
        return 0;
    }
}