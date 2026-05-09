package appLogic.project;

import java.util.Set;

import appLogic.activity.impl.Activity;
import appLogic.employee.Employee;

public class ProjectActivity extends Activity {
    private Set<Employee> assignedEmployees;
    private int hoursExpected;
    private ActivityStatus status;

    public ProjectActivity(String name) {
        this(name, "", "", 0, 0, 0, 0, null);
    }

    public ProjectActivity(String name, String description, String summary,
                           int startWeek, int endWeek, int startYear, int endYear,
                           String projectId) {
        super(name, description, summary, startWeek, endWeek, startYear, endYear, projectId);
    }

    public ActivityStatus getStatus() {
        return status;
    }

    public void setStatus(ActivityStatus status) {
        this.status = status;
    }

    public boolean isOverlapWeek(int week, int year) {
        if (year < getStartYear() || year > getEndYear()) return false;
        if (getStartYear() == getEndYear()) return week >= getStartWeek() && week <= getEndWeek();
        if (year == getStartYear()) return week >= getStartWeek();
        if (year == getEndYear()) return week <= getEndWeek();
        return true;
    }
}

enum ActivityStatus {
    DONE,
    PROGRESS,
    BACKLOG
}
