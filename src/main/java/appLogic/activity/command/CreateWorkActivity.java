package appLogic.activity.command;

import java.time.LocalDate;
import java.util.UUID;

public record CreateWorkActivity(UUID projectId, String name, String description, String summary, LocalDate date) {
}
