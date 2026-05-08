package appLogic.activity.impl;

import appLogic.FixedActivityType;

import java.time.LocalDateTime;

public class FixedActivity extends Activity {

    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private final FixedActivityType type;

    public FixedActivity(String name, String description, String summary,
                         LocalDateTime startDate, LocalDateTime endDate,
                         FixedActivityType type) {
        super(name, description, summary, startDate, endDate, null);
        this.startDate = startDate;
        this.endDate = endDate;
        this.type = type;
    }

    public FixedActivityType getType() {
        return type;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }
}