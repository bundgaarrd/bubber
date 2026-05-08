package appLogic.activity.command;

import java.time.LocalDateTime;

public record CreateProjectActivity(String projectId, String name, String description, String summary,
                                    LocalDateTime startDate, LocalDateTime endDate, double expectedHours) {
}
