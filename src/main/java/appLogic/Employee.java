package appLogic;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Employee {

    private final String initials;
    private final String name;
    private boolean isAvailable;
    private final List<Activity> activities = new ArrayList<>();

    public Employee(String initials, String name, boolean isAvailable) {
        this.initials = initials;
        this.name = name;
        this.isAvailable = isAvailable;
    }

    public String getInitials() {
        return initials;
    }

    public String getName() {
        return name;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public void addActivity(Activity activity) {
        activities.add(activity);
        activity.assignEmployee(this);
    }

    public int getActiveActivityCount(int week, int year) {
        int count = 0;
        WeekFields weekFields = WeekFields.of(Locale.getDefault());

        for (Activity activity : activities) {
            LocalDate date = activity.getDate();

            int activityWeek = date.get(weekFields.weekOfWeekBasedYear());
            int activityYear = date.getYear();

            if (activityWeek == week && activityYear == year) {
                count++;
            }
        }

        return count;
    }
}