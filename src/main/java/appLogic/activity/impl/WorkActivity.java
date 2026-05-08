package appLogic.activity.impl; //s244813

public class WorkActivity extends Activity {

    public WorkActivity(String name, String description, String summary, int startWeek, int endWeek, int startYear, int endYear) {
        super(name, description, summary, startWeek, endWeek, startYear, endYear, null);
    }

    @Override
    public int getDuration() {
        return 0;
    }
}