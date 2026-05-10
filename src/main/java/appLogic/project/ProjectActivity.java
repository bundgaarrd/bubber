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
        LocalDateTime startDate = getStartDate();
        LocalDateTime endDate = getEndDate();

        LocalDateTime weekStart = LocalDateTime.of(year, 1, 1, 0, 0)
                .with(java.time.temporal.TemporalAdjusters.firstInMonth(java.time.DayOfWeek.MONDAY))
                .plusWeeks(week - 1);
        LocalDateTime weekEnd = weekStart.plusDays(6).withHour(23).withMinute(59).withSecond(59);

        return (startDate.isBefore(weekEnd) && endDate.isAfter(weekStart));
    }
}

enum ActivityStatus {
    DONE,
    PROGRESS,
    BACKLOG
}
