package appLogic; //s244813

import appLogic.activity.impl.Activity;
import appLogic.employee.Employee;

import java.time.LocalDateTime;

public class TimeEntry {

    private Employee employee;
    private Activity activity;
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

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
}