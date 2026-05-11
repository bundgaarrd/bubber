package appLogic.activity.command; // Lavet af Valdemar (s246575)

import java.time.LocalDateTime;

public record CreateWorkActivity(String projectId, String name, String description, String summary,
                                 LocalDateTime startDate, LocalDateTime endDate, double expectedHours) {}
