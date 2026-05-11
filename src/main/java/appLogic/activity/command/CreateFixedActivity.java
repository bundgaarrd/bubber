package appLogic.activity.command; // Lavet af Andreas (s244970) og Valdemar (s246575)

import appLogic.FixedActivityType;

import java.time.LocalDateTime;

public record CreateFixedActivity(LocalDateTime startDate, LocalDateTime endDate, FixedActivityType type) {
}
