package appLogic.activity.command;

import appLogic.FixedActivityType;

import java.time.LocalDate;
import java.util.UUID;

public record CreateFixedActivity(String projectId, String name, String description, String summary,
                                  LocalDate startDate, LocalDate endDate, FixedActivityType type) {
}
