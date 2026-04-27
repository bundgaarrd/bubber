package appLogic;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Locale;

public class FixedActivity extends Activity {

    private final LocalDate startDate;
    private final LocalDate endDate;
    private final FixedActivityType type;

    public FixedActivity(String description, String summary,
                         LocalDate startDate, LocalDate endDate,
                         FixedActivityType type) {
        super(description, summary, startDate);
        this.startDate = startDate;
        this.endDate = endDate;
        this.type = type;
    }

    public FixedActivityType getType() {
        return type;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public boolean isOverlappingWeek(int week, int year) {
        WeekFields weekFields = WeekFields.of(Locale.getDefault());

        LocalDate startOfWeek = LocalDate.now()
                .withYear(year)
                .with(weekFields.weekOfWeekBasedYear(), week)
                .with(weekFields.dayOfWeek(), 1);

        LocalDate endOfWeek = startOfWeek.plusDays(6);

        return !(endDate.isBefore(startOfWeek) || startDate.isAfter(endOfWeek));
    }
}