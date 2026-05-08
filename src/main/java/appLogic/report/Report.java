package appLogic.report;

import java.util.Set;

import appLogic.Summary;

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
