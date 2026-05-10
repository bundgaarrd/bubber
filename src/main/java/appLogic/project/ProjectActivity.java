package appLogic.project;

import appLogic.activity.impl.Activity;
import appLogic.employee.Employee;

import java.time.LocalDateTime;
import java.util.Set;

public class ProjectActivity extends Activity {
    private Set<Employee> assignedEmployees;
    private int hoursExpected;
    private ActivityStatus status;

    public ProjectActivity(String name) {
        this(name, "", "", LocalDateTime.now(), LocalDateTime.now().plusDays(7), null);
    }

    public ProjectActivity(String name, String description, String summary,
                           LocalDateTime startDate, LocalDateTime endDate, String projectId) {
        super(name, description, summary, startDate, endDate, projectId);
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
