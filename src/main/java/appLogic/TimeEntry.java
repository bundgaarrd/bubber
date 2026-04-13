package appLogic;

import java.time.LocalDateTime;

public class TimeEntry {
    private Employee employee;
    private Activity activity;
    private LocalDateTime entryStartTime;
    private double hoursWorked;

    public TimeEntry(Employee employee, Activity activity) {
        this.employee = employee;
        this.activity = activity;
    }

    public void setEntryStartTime(LocalDateTime time) {
        this.entryStartTime = time;
    }

    public void setHoursWorked(double hours) {
        this.hoursWorked = hours;
    }

    public double getHoursWorked() {
        return hoursWorked;
    }

    public Activity getActivity() {
        return activity;
    }

    // Optional getters
    public Employee getEmployee() {
        return employee;
    }

    public LocalDateTime getEntryStartTime() {
        return entryStartTime;
    }
}