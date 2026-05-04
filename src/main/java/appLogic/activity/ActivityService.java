package appLogic.activity;

import appLogic.Activity;
import appLogic.TimeEntry;
import appLogic.activity.command.CreateFixedActivity;
import appLogic.activity.command.CreateProjectActivity;
import appLogic.activity.command.CreateWorkActivity;
import appLogic.employee.Employee;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface ActivityService {
    Activity createProjectActivity(CreateProjectActivity command);

    Activity createWorkActivity(CreateWorkActivity command);

    Activity createFixedActivity(CreateFixedActivity command);

    Activity findByProjectAndName(UUID projectId, String activityName);

    List<Activity> findByProject(UUID projectId);

    void deleteActivity(UUID projectId, String activityName);

    void assignEmployee(UUID activityId, Employee employee);

    TimeEntry registerWork(UUID activityId, Employee employee, LocalDateTime entryStart, double hoursWorked);
}
