package appLogic.activity.command;

import java.time.LocalDate;
import java.util.UUID;

public record CreateWorkActivity(String projectId, String name, String description, String summary, LocalDate date) {
}
