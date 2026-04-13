package appLogic;

import java.util.Set;

public class ProjectActivity {
    private Set<Employee> assignedEmployees;
    private int hoursExpected;
    private ActivityStatus status;
    private int startWeek;
    private int startYear;
    private int endWeek;
    private int endYear;

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