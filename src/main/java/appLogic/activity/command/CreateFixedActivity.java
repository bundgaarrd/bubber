package appLogic.activity.command;

import appLogic.FixedActivityType;

import java.time.LocalDateTime;

public record CreateFixedActivity(String projectId, String name, String description, String summary,
                                  LocalDateTime startDate, LocalDateTime endDate, FixedActivityType type) {
}
