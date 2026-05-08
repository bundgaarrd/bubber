package appLogic.activity.impl;

import appLogic.FixedActivityType;

public class FixedActivity extends Activity {

    private final int startWeek;
    private final int endWeek;
    private final FixedActivityType type;

    public FixedActivity(String name, String description, String summary,
                         int startWeek, int endWeek, int startYear, int endYear,
                         FixedActivityType type) {
        super(name, description, summary, startWeek, endWeek, startYear, endYear, null);
        this.startWeek = startWeek;
        this.endWeek = endWeek;
        this.type = type;
    }

    public FixedActivityType getType() {
        return type;
    }

    public int getStartWeek() {
        return startWeek;
    }

    public int getEndWeek() {
        return endWeek;
    }
}