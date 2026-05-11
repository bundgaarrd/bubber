package appLogic.activity.command;

import java.time.LocalDateTime;

import appLogic.activity.FixedActivityType;

public record CreateFixedActivity(LocalDateTime startDate, LocalDateTime endDate, FixedActivityType type) {
}
