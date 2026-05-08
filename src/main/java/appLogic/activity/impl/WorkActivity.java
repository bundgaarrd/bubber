package appLogic.activity.impl; //s244813

import java.time.LocalDateTime;

public class WorkActivity extends Activity {
    private final double expectedHours;

    public WorkActivity(String name, String description, String summary,
                        LocalDateTime startDate, LocalDateTime endDate, double expectedHours) {
        super(name, description, summary, startDate, endDate, null);
        this.expectedHours = expectedHours;
    }

    public double getExpectedHours() {
        return expectedHours;
    }
}