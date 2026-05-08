package appLogic.activity.command;

public record CreateProjectActivity(String projectId, String name, String description, String summary,
                                    int startWeek, int endWeek, int startYear, int endYear) {
}
