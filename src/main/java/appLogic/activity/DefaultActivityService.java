package appLogic.activity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import appLogic.TimeEntry;
import appLogic.activity.command.CreateFixedActivity;
import appLogic.activity.command.CreateProjectActivity;
import appLogic.activity.command.CreateWorkActivity;
import appLogic.activity.exception.ActivityNotFoundException;
import appLogic.activity.exception.DuplicateActivityException;
import appLogic.activity.exception.InvalidHoursException;
import appLogic.activity.exception.UnauthorizedActivityAccessException;
import appLogic.activity.impl.Activity;
import appLogic.activity.impl.FixedActivity;
import appLogic.activity.impl.WorkActivity;
import appLogic.employee.Employee;
import appLogic.employee.EmployeeRepository;
import appLogic.employee.InMemoryTimeEntryRepository;
import appLogic.project.Project;
import appLogic.project.ProjectActivity;
import appLogic.project.ProjectRegistry;


public class DefaultActivityService implements ActivityService {
    private final ActivityRepository activityRepository;
    private final ProjectRegistry projectRegistry;
    private final CurrentUserProvider currentUserProvider;
    private final InMemoryTimeEntryRepository timeEntryRepository;
    private final EmployeeRepository employeeRepository;

    public DefaultActivityService(ActivityRepository activityRepository,
                                  ProjectRegistry projectRegistry,
                                  CurrentUserProvider currentUserProvider,
                                  InMemoryTimeEntryRepository timeEntryRepository,
                                  EmployeeRepository employeeRepository) {
        this.activityRepository = activityRepository;
        this.projectRegistry = projectRegistry;
        this.currentUserProvider = currentUserProvider;
        this.timeEntryRepository = timeEntryRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Activity createProjectActivity(CreateProjectActivity command) {
        Project project = requireProject(command.projectId());
        requireProjectLeader(project);
        ensureUniqueName(command.projectId(), command.name());
        Activity activity = new ProjectActivity(command.name(), command.description(), command.summary(),
                command.startWeek(), command.endWeek(), command.startYear(), command.endYear());
        activityRepository.save(command.projectId(), activity);
        return activity;
    }

    @Override
    public Activity createWorkActivity(CreateWorkActivity command) {
        requireProject(command.projectId());
        ensureUniqueName(command.projectId(), command.name());
        Activity activity = new WorkActivity(
                command.name(), command.description(), command.summary(),
                command.startWeek(), command.endWeek(), command.startYear(), command.endYear(), command.expectedHours()
        );
        activityRepository.save(command.projectId(), activity);
        return activity;
    }

    @Override
    public Activity createFixedActivity(CreateFixedActivity command) {
        requireProject(command.projectId());
        ensureUniqueName(command.projectId(), command.name());
        Activity activity = new FixedActivity(command.name(), command.description(), command.summary(),
                command.startWeek(), command.endWeek(), command.startYear(), command.endYear(), command.type());
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

    @Override
    public void saveTimeEntry(String projectId,
                                      String initials,
                                      String description,
                                      String summary,
                                      int startWeek,
                                      int endWeek,
                                      int startYear,
                                      int endYear,
                                      double hours) {

        Employee emp = employeeRepository.findByInitials(initials);

        if (emp == null) {
            throw new IllegalArgumentException("No such employee when saving time entry");
        }

        Activity activity = createWorkActivity(new CreateWorkActivity(
                projectId,
                initials + "-" + description,
                description,
                summary,
                startWeek,
                endWeek,
                startYear,
                endYear,
                (int) hours
        ));

        registerWork(activity.getId(), emp, LocalDateTime.now(), hours);
    }
    
}
