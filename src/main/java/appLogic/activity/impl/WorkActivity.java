package appLogic.activity.impl; //s244813

public class WorkActivity extends Activity {
    private final double expectedHours;

    public WorkActivity(String name, String description, String summary,
                        int startWeek, int endWeek, int startYear, int endYear,
                        double expectedHours, String projectId) {
        super(name, description, summary, startWeek, endWeek, startYear, endYear, projectId);
        this.expectedHours = expectedHours;
    }

    public double getExpectedHours() {
        return expectedHours;
    }
}