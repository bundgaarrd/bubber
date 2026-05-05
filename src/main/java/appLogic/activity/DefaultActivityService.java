package appLogic.activity;

import appLogic.*;
import appLogic.activity.command.CreateFixedActivity;
import appLogic.activity.command.CreateProjectActivity;
import appLogic.activity.command.CreateWorkActivity;
import appLogic.activity.exception.ActivityNotFoundException;
import appLogic.activity.exception.DuplicateActivityException;
import appLogic.activity.exception.InvalidHoursException;
import appLogic.activity.exception.UnauthorizedActivityAccessException;
import appLogic.employee.Employee;
import appLogic.employee.InMemoryTimeEntryRepository;
import appLogic.project.Project;
import appLogic.project.ProjectActivity;
import appLogic.project.ProjectRegistry;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class DefaultActivityService implements ActivityService {
    private final ActivityRepository activityRepository;
    private final ProjectRegistry projectRegistry;
    private final CurrentUserProvider currentUserProvider;
    private final InMemoryTimeEntryRepository timeEntryRepository;

    public DefaultActivityService(ActivityRepository activityRepository,
                                  ProjectRegistry projectRegistry,
                                  CurrentUserProvider currentUserProvider,
                                  InMemoryTimeEntryRepository timeEntryRepository) {
        this.activityRepository = activityRepository;
        this.projectRegistry = projectRegistry;
        this.currentUserProvider = currentUserProvider;
        this.timeEntryRepository = timeEntryRepository;
    }

    @Override
    public Activity createProjectActivity(CreateProjectActivity command) {
        Project project = requireProject(command.projectId());
        requireProjectLeader(project);
        ensureUniqueName(command.projectId(), command.name());
        Activity activity = new ProjectActivity(command.name(), command.description(), command.summary(), command.date());
        activityRepository.save(command.projectId(), activity);
        return activity;
    }

    @Override
    public Activity createWorkActivity(CreateWorkActivity command) {
        requireProject(command.projectId());
        ensureUniqueName(command.projectId(), command.name());
        Activity activity = new WorkActivity(command.name(), command.description(), command.summary(), command.date());
        activityRepository.save(command.projectId(), activity);
        return activity;
    }

    @Override
    public Activity createFixedActivity(CreateFixedActivity command) {
        requireProject(command.projectId());
        ensureUniqueName(command.projectId(), command.name());
        Activity activity = new FixedActivity(command.name(), command.description(), command.summary(),
                command.startDate(), command.endDate(), command.type());
        activityRepository.save(command.projectId(), activity);
        return activity;
    }

    @Override
    public Activity findByProjectAndName(String projectId, String activityName) {
        return activityRepository.findByProjectAndName(projectId, activityName)
                .orElseThrow(() -> new ActivityNotFoundException("Activity not found."));
    }

    @Override
    public List<Activity> findByProject(String projectId) {
        Project project = requireProject(projectId);
        requireProjectLeader(project);
        return activityRepository.findByProject(projectId);
    }

    @Override
    public void deleteActivity(String projectId, String activityName) {
        Project project = requireProject(projectId);
        requireProjectLeader(project);
        Activity activity = findByProjectAndName(projectId, activityName);
        activityRepository.delete(activity.getId());
    }

    @Override
    public void assignEmployee(UUID activityId, Employee employee) {
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new ActivityNotFoundException("Activity not found."));
        if (!activity.assignEmployee(employee)) {
            return;
        }
        if (!employee.getActivities().contains(activity)) {
            employee.getActivities().add(activity);
        }
    }

    @Override
    public TimeEntry registerWork(UUID activityId, Employee employee, LocalDateTime entryStart, double hoursWorked) {
        if (hoursWorked < 0) {
            throw new InvalidHoursException("Invalid hours");
        }
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new ActivityNotFoundException("Activity not found."));
        assignEmployee(activityId, employee);
        TimeEntry entry = new TimeEntry(employee, activity, entryStart, hoursWorked);
        timeEntryRepository.save(entry);
        return entry;
    }

    private void ensureUniqueName(String projectId, String name) {
        if (activityRepository.findByProjectAndName(projectId, name).isPresent()) {
            throw new DuplicateActivityException("Activity with the same name already exists in this project.");
        }
    }

    private Project requireProject(String projectId) {
        Project project = projectRegistry.getProjectById(projectId);
        if (project == null) {
            throw new IllegalStateException("Project not found.");
        }
        return project;
    }

    private void requireProjectLeader(Project project) {
        Employee currentUser = currentUserProvider.getCurrentUser();
        if (currentUser == null || !currentUser.equals(project.getProjectLeader())) {
            throw new UnauthorizedActivityAccessException("Only project leader can access activities.");
        }
    }
}
