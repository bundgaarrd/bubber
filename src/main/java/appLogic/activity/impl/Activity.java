package appLogic.activity.impl;

import appLogic.TimeEntry;
import appLogic.activity.exception.UnavailableEmployeeException;
import appLogic.employee.Employee;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public abstract class Activity {

    private final UUID id;
    private final String name;
    private final String description;
    private final String summary;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private final Set<TimeEntry> entries = new HashSet<>();
    private final String projectId;

    public Activity(String name, String description, String summary, LocalDateTime startDate, LocalDateTime endDate, String projectId) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.description = description;
        this.summary = summary;
        this.startDate = startDate;
        this.endDate = endDate;
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

    public Set<TimeEntry> getTimeEntries() {
        return entries;
    }

    public Set<Employee> getAssignedEmployees() {
        return getTimeEntries().stream()
                .map(TimeEntry::getEmployee)
                .collect(java.util.stream.Collectors.toSet());
    }

    @Deprecated
    public boolean assignEmployee(Employee emp) {
        return assignEmployee(new TimeEntry(
                emp,
                this,
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(14),
                5
        ));
    }

    public boolean assignEmployee(TimeEntry entry) {
        Employee emp = entry.getEmployee();
        if(!emp.isAvailable()) throw new UnavailableEmployeeException("Employee is unavailable.");
        return entries.add(entry);
    }

    public boolean removeEmployee(Employee emp) {
        return entries.removeIf(entry -> entry.getEmployee().equals(emp));
    }

    public String getDescription() {
        return description;
    }

    public String getSummary() {
        return summary;
    }

    public int getDuration() { //TODO Add to UI
        return Math.toIntExact(endDate.toEpochSecond(ZoneOffset.UTC) - startDate.toEpochSecond(ZoneOffset.UTC));
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }
}