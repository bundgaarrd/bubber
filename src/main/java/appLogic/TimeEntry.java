package appLogic;

import java.time.LocalDateTime;

public class TimeEntry {

    private final Employee employee;
    private final Activity activity;
    private LocalDateTime entryStartTime;
    private double hoursWorked;

    public TimeEntry(Employee employee, Activity activity, LocalDateTime entryStartTime, double hoursWorked) {
        this.employee = employee;
        this.activity = activity;
        this.entryStartTime = entryStartTime;
        this.hoursWorked = hoursWorked;
    }

    public Employee getEmployee() {
        return employee;
    }

    public Activity getActivity() {
        return activity;
    }

    public LocalDateTime getEntryStartTime() {
        return entryStartTime;
    }

    public void setEntryStartTime(LocalDateTime entryStartTime) {
        this.entryStartTime = entryStartTime;
    }

    public double getHoursWorked() {
        return hoursWorked;
    }

    public void setHoursWorked(double hoursWorked) {
        this.hoursWorked = hoursWorked;
    }
}