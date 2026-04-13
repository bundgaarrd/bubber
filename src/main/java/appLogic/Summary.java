package appLogic;

public class Summary {
    private double hoursUsed;
    private String activitySummary;

    public Summary(double hoursUsed, String activitySummary) {
        this.hoursUsed = hoursUsed;
        this.activitySummary = activitySummary;
    }

    public double getHoursUsed() {
        return hoursUsed;
    }

    public String getActivitySummary() {
        return activitySummary;
    }
}
