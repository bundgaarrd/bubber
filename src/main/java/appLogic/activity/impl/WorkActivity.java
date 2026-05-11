package appLogic.activity.impl;

import java.time.LocalDateTime;

public class WorkActivity extends Activity {
    private final double expectedHours;

    public WorkActivity(String name, String description, String summary,
                        LocalDateTime startDate, LocalDateTime endDate, double expectedHours, String projectId) {
        super(name, description, summary, startDate, endDate, projectId);
        this.expectedHours = expectedHours;
    }

    public double getExpectedHours() {
        return expectedHours;
    }

}