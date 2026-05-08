package appLogic.activity.impl; //s244813

public class WorkActivity extends Activity {
    private final int expectedHours;

    public WorkActivity(String name, String description, String summary,
                        int startWeek, int endWeek, int startYear, int endYear, int expectedHours) {
        super(name, description, summary, startWeek, endWeek, startYear, endYear, null);
        this.expectedHours = expectedHours;
    }

    public int getExpectedHours() {
        return expectedHours;
    }
}