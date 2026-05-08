package appLogic.activity.command;

import java.time.LocalDateTime;

public record CreateWorkActivity(String projectId, String name, String description, String summary,
                                 LocalDateTime startDate, LocalDateTime endDate, int expectedHours) {}
