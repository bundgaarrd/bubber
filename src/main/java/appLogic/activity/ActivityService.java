package appLogic.activity; // Lavet af Valdemar (s246575) og Andreas (s244970)

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import appLogic.activity.impl.Activity;
import appLogic.TimeEntry;
import appLogic.activity.command.CreateFixedActivity;
import appLogic.activity.command.CreateWorkActivity;
import appLogic.employee.Employee;

public interface ActivityService {
    Activity createWorkActivity(CreateWorkActivity command);

    Activity createFixedActivity(CreateFixedActivity command);

    Activity findByProjectAndName(String projectId, String activityName);

    List<Activity> findByProject(String projectId);

    void deleteActivity(String projectId, String activityName);

    void deleteEntry(TimeEntry entry);

    void assignEmployee(UUID activityId, TimeEntry entry);

    TimeEntry registerWork(UUID activityId, Employee employee, LocalDateTime entryStart, LocalDateTime entryEnd, double hoursWorked);

    double getRemainingHours(String projectId);

    void saveTimeEntry(String projectId, String initials, String description, String summary,
                       LocalDateTime startDate, LocalDateTime endDate, double hours);
}
