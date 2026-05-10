package appLogic.activity.impl;

import appLogic.FixedActivityType;

import java.time.LocalDateTime;
import java.time.temporal.WeekFields;
import java.util.Arrays;
import java.util.Locale;

public class FixedActivity extends Activity {

    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private final FixedActivityType type;

    public FixedActivity(LocalDateTime startDate, LocalDateTime endDate, FixedActivityType type) {
        super("", type.name(),"", startDate, endDate, "");
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

    public int getStartWeek() {
        WeekFields wf = WeekFields.of(Locale.getDefault());
        return startDate.toLocalDate().get(wf.weekOfWeekBasedYear());
    }

    public int getEndWeek() {
        WeekFields wf = WeekFields.of(Locale.getDefault());
        return endDate.toLocalDate().get(wf.weekOfWeekBasedYear());
    }

    public int getStartYear() {
        return startDate.getYear();
    }

    public int getEndYear() {
        return endDate.getYear();
    }
}