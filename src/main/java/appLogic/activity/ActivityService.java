package appLogic.activity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import appLogic.activity.impl.Activity;
import appLogic.TimeEntry;
import appLogic.activity.command.CreateFixedActivity;
import appLogic.activity.command.CreateProjectActivity;
import appLogic.activity.command.CreateWorkActivity;
import appLogic.employee.Employee;

public interface ActivityService {
    Activity createProjectActivity(CreateProjectActivity command);

    Activity createWorkActivity(CreateWorkActivity command);

    Activity createFixedActivity(CreateFixedActivity command);

    Activity findByProjectAndName(String projectId, String activityName);

    List<Activity> findByProject(String projectId);

    void deleteActivity(String projectId, String activityName);

    void assignEmployee(UUID activityId, Employee employee);

    TimeEntry registerWork(UUID activityId, Employee employee, LocalDateTime entryStart, double hoursWorked);

    void saveTimeEntry(String projectId, String initials, String description, String summary,
                       int startWeek, int endWeek, int startYear, int endYear, double hours);
}
