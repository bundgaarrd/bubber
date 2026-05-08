package appLogic.activity.command;

import java.time.LocalDate;

public record CreateProjectActivity(String projectId, String name, String description, String summary, LocalDate date) {
}
