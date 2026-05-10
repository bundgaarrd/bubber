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
}