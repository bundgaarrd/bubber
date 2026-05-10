package appLogic.activity;

import appLogic.TimeEntry;
import appLogic.activity.command.CreateFixedActivity;
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
import appLogic.project.ProjectRegistry;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


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
    public Activity createWorkActivity(CreateWorkActivity command) {
        requireProject(command.projectId());
        ensureUniqueName(command.projectId(), command.name());
        Activity activity = new WorkActivity(
                command.name(), command.description(), command.summary(),
                command.startDate(), command.endDate(), command.expectedHours(), command.projectId()
        );
        activityRepository.save(command.projectId(), activity);
        return activity;
    }

    @Override
    public Activity createFixedActivity(CreateFixedActivity command) {
        FixedActivity activity = new FixedActivity(command.startDate(), command.endDate(), command.type());
        activityRepository.save(activity.getType().toString(), activity);
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
    public void deleteEntry(TimeEntry entry) {
        Employee employee = entry.getEmployee();
        employee.getEntries().remove(entry);
        timeEntryRepository.remove(entry);
    }

    @Override
    public void assignEmployee(UUID activityId, TimeEntry entry) {
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new ActivityNotFoundException("Activity not found."));
        if (!activity.assignEmployee(entry)) {
            return;
        }

        Employee employee = entry.getEmployee();
        employee.addEntry(entry);
        // Keep employee activities in sync
        employee.addActivity(activity);
    }

    @Override
    public TimeEntry registerWork(UUID activityId, Employee employee, LocalDateTime entryStart, LocalDateTime entryEnd, double hoursWorked) {
        if (hoursWorked < 0) {
            throw new InvalidHoursException("Invalid hours");
        }
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new ActivityNotFoundException("Activity not found."));
        TimeEntry entry = new TimeEntry(employee, activity, entryStart, entryEnd, hoursWorked);
        assignEmployee(activityId, entry);
        try { timeEntryRepository.save(entry); } catch (Exception ignored) {}
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
    public double getRemainingHours(String projectId) {
        double totalExpected = activityRepository.findByProject(projectId).stream()
                .filter(a -> a instanceof WorkActivity)
                .mapToDouble(a -> ((WorkActivity) a).getExpectedHours())
                .sum();

        double totalLogged = timeEntryRepository.findAll().stream()
                .filter(entry -> projectId.equals(entry.getActivity().getProjectId()))
                .mapToDouble(TimeEntry::getHoursWorked)
                .sum();

        return totalExpected - totalLogged;
    }

    @Override
    public void saveTimeEntry(String projectId,
                              String initials,
                              String description,
                              String summary,
                              LocalDateTime startDate,
                              LocalDateTime endDate,
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
                startDate,
                endDate,
                (int) hours
        ));

        registerWork(activity.getId(), emp, startDate, endDate, hours);
    }
}
