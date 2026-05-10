package appLogic.activity.command;

import appLogic.FixedActivityType;

import java.time.LocalDateTime;

public record CreateFixedActivity(LocalDateTime startDate, LocalDateTime endDate, FixedActivityType type) {
}
