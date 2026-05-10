package appLogic.activity.impl; //s244813

import java.time.LocalDateTime;
import java.time.DayOfWeek;

public class WorkActivity extends Activity {
    private final double expectedHours;

    // ActivityStatus and status were previously on ProjectActivity; include them here
    public enum ActivityStatus {
        DONE,
        PROGRESS,
        BACKLOG
    }

    private ActivityStatus status;

    public WorkActivity(String name, String description, String summary,
                        LocalDateTime startDate, LocalDateTime endDate, double expectedHours, String projectId) {
        super(name, description, summary, startDate, endDate, projectId);
        this.expectedHours = expectedHours;
    }

    public double getExpectedHours() {
        return expectedHours;
    }

    public ActivityStatus getStatus() {
        return status;
    }

    public void setStatus(ActivityStatus status) {
        this.status = status;
    }

    // Keep previous helper for tests/compatibility: determines if activity overlaps a week/year
    public boolean isOverlapWeek(int week, int year) {
        LocalDateTime startDate = getStartDate();
        LocalDateTime endDate = getEndDate();

        LocalDateTime weekStart = LocalDateTime.of(year, 1, 1, 0, 0)
                .with(java.time.temporal.TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY))
                .plusWeeks(week - 1);
        LocalDateTime weekEnd = weekStart.plusDays(6).withHour(23).withMinute(59).withSecond(59);

        return ( !startDate.isAfter(weekEnd) ) && ( !endDate.isBefore(weekStart) );
    }
}