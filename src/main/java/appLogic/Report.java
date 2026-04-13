package appLogic;

import java.util.Set;

public class Report {
    private double hoursUsed;
    private Set<Summary> activitySummaries;
    private int expectedRemainingHours;
    
    public Report(double hoursUsed, Set<Summary> activitySummaries, int expectedRemainingHours) {
        this.hoursUsed = hoursUsed;
        this.activitySummaries = activitySummaries;
        this.expectedRemainingHours = expectedRemainingHours;
    }

    public double getHoursUsed() {
        return hoursUsed;
    }

    public Set<Summary> getActivitySummaries() {
        return activitySummaries;
    }

    public int getExpectedRemainingHours() {
        return expectedRemainingHours;
    }

}
