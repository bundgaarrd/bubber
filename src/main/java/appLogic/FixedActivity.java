package appLogic;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Locale;

public class FixedActivity {
    private LocalDate startDate;
    private LocalDate endDate; 
    private FixedActivityType type;

    public boolean isOverlappingWeek(int week, int year) {
        WeekFields weekFields = WeekFields.of(Locale.getDefault());

        LocalDate startOfWeek = LocalDate.now()
                .withYear(year)
                .with(weekFields.weekOfYear(), week)
                .with(weekFields.dayOfWeek(), 1);

        LocalDate endOfWeek = startOfWeek.plusDays(6);

        return !(endDate.isBefore(startOfWeek) || startDate.isAfter(endOfWeek));
    }
}

enum FixedActivityType {
    SICK,
    VACATION,
    COURSE,
    OTHER
}