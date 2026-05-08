package appLogic.project;

import appLogic.activity.impl.Activity;
import appLogic.employee.Employee;

import java.time.LocalDate;
import java.util.Set;

public class ProjectActivity extends Activity {
    private Set<Employee> assignedEmployees;
    private int hoursExpected;
    private ActivityStatus status;
    private int startWeek;
    private int startYear;
    private int endWeek;
    private int endYear;

    public ProjectActivity(String name) {
        this(name, "", "", null);
    }

    public ProjectActivity(String name, String description, String summary, LocalDate date) {
        super(name, description, summary, date);
    }

    public ActivityStatus getStatus() {
        return status;
    }

    public void setStatus(ActivityStatus status) {
        this.status = status;
    }

    public boolean isOverlapWeek(int week, int year) {
        
        if (year < startYear || year > endYear) {
            return false;
        }

        if (startYear == endYear) {
            return week >= startWeek && week <= endWeek;
        }

        if (year == startYear) {
            return week >= startWeek;
        }

        if (year == endYear) {
            return week <= endWeek;
        }

        return true; 
    }
}

enum ActivityStatus {
    DONE,
    PROGRESS,
    BACKLOG
}