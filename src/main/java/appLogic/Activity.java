package appLogic;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public abstract class Activity {
    private UUID id;
    private String description;
    private String summary;
    private LocalDate date;
    private Set<Employee> assignedEmployees = new HashSet<>();

    public Activity(String description, String summary, LocalDate date) {
        this.id = UUID.randomUUID();
        this.description = description;
        this.summary = summary;
        this.date = date;
    }

    public UUID getId() {
        return id;
    }

    public String getProjectReferenceId() {
        return id.toString(); 
    }

    public Set<Employee> getAssignedEmployees() {
        return assignedEmployees;
    }

    public boolean assignEmployee(Employee emp) {
        return assignedEmployees.add(emp);
    }

    public int getHoursExpected() {
        return 0; 
    }

    public String getDescription() {
        return description;
    }

    public String getSummary() {
        return summary;
    }

    public int getDuration() {
        return 0; 
    }

    public LocalDate getDate() {
        return date;
    }
}