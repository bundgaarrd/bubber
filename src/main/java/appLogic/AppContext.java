package appLogic; //s244813

import appLogic.activity.ActivityService;
import appLogic.activity.DefaultActivityService;
import appLogic.activity.InMemoryActivityRepository;
import appLogic.activity.command.CreateWorkActivity;
import appLogic.employee.Employee;
import appLogic.employee.InMemoryEmployeeRepository;
import appLogic.employee.InMemoryTimeEntryRepository;
import appLogic.project.Project;
import appLogic.project.ProjectRegistry;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class AppContext {
    private static AppContext instance = null;

    private InMemoryEmployeeRepository employeeRepository;
    private InMemoryTimeEntryRepository timeEntryRepository;
    private InMemoryActivityRepository activityRepository;
    private ProjectRegistry projectRegistry;
    private Employee loggedInUser;
    private ActivityService activityService;

    private AppContext() {
        instance = this;
    }

    public static AppContext initialize() {
         if(instance == null) {
             instance = new AppContext();
             instance.loadContext();
             instance.loadEmployees();
         }
         return instance;
    }

    private void loadContext() {
        employeeRepository = new InMemoryEmployeeRepository();
        timeEntryRepository = new InMemoryTimeEntryRepository();
        activityRepository = new InMemoryActivityRepository();
        projectRegistry = new ProjectRegistry();
        activityService = new DefaultActivityService(
                new InMemoryActivityRepository(),
                projectRegistry,
                () -> loggedInUser,
                timeEntryRepository
        );
    }

    private void loadEmployees() {
        employeeRepository.loadFromFile("employees.txt");

        Project contextProject = projectRegistry.createProject("AppContext");
        contextProject.assignProjectLeader(loggedInUser);

        saveTimeEntry(contextProject.getProjectID(), "huba", "Being a good teacher", "TDD/BDD forelæsning", 2.5);
        saveTimeEntry(contextProject.getProjectID(), "wilo", "Being a good TA", "Explaining TDD issues", 1.5);

        Project KBHShop = projectRegistry.createProject("KBHShop"); // generates 26001
        KBHShop.assignProjectLeader(employeeRepository.findByInitials("laha"));

        Project DTU = projectRegistry.createProject("DTU");         // generates 26002
        DTU.assignProjectLeader(employeeRepository.findByInitials("alla"));
    }

    public void login(String initials) {
        if (initials == null || initials.length() > 4) {
            throw new IllegalArgumentException("Initials must be 1-4 characters");
        }

        Employee emp = employeeRepository.findByInitials(initials);
        if (emp == null) {
            throw new IllegalStateException("Employee not found");
        }

        this.loggedInUser = emp;
    }

    private void saveTimeEntry(String projectId,
                                      String initials,
                                      String description,
                                      String summary,
                                      double hours) {

        Employee emp = employeeRepository.findByInitials(initials);

        if (emp == null) {
            System.out.println("Employee not found: " + initials);
            return;
        }

        Activity activity = activityService.createWorkActivity(new CreateWorkActivity(
                projectId,
                initials + "-" + description,
                description,
                summary,
                LocalDate.now()
        ));

        activityService.registerWork(activity.getId(), emp, LocalDateTime.now(), hours);
    }

    public InMemoryEmployeeRepository getEmployeeRepository() {
        return employeeRepository;
    }

    public InMemoryTimeEntryRepository getTimeEntryRepository() {
        return timeEntryRepository;
    }

    public ProjectRegistry getProjectRegistry() {
        return projectRegistry;
    }

    public ActivityService getActivityService() {
        return activityService;
    }

    public Employee getLoggedInUser() {
        return loggedInUser;
    }

    public void reset() {
        instance = null;
        initialize();
    }
}
