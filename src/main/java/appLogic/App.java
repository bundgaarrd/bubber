package appLogic;

import appLogic.employee.Employee;
import appLogic.employee.EmployeeRepository;
import appLogic.employee.InMemoryEmployeeRepository;
import appLogic.employee.InMemoryTimeEntryRepository;
import appLogic.activity.*;
import appLogic.project.Project;
import appLogic.project.ProjectRegistry;

import java.util.*;

public class App {
    private static App instance;

    private final ProjectRegistry projectRegistry;
    private final EmployeeRepository employeeRepository;
    private final InMemoryTimeEntryRepository timeEntryRepository;
    private final ActivityService activityService;
    private final ActivityRepository activityRepository;
    private Employee loggedInUser;

    public static void main(String[] args) { // Has to be run from mvn javafx:run
        System.out.println("Starting the application ...");
        instance = getInstance();
    }

    private void run() {

    }

    private App() {
        this.employeeRepository = new InMemoryEmployeeRepository();
        this.projectRegistry = new ProjectRegistry();
        this.timeEntryRepository = new InMemoryTimeEntryRepository();
        this.activityRepository = new InMemoryActivityRepository();
        this.activityService = new DefaultActivityService(
                this.activityRepository,
                this.projectRegistry,
                this::getLoggedInUser,
                this.timeEntryRepository
        );
    }

    private void initializeUsers() {
        employeeRepository.save(new Employee("huba", "Hubert Baumeister", true));
        employeeRepository.save(new Employee("wilo", "William Lopez", true));
        employeeRepository.save(new Employee("anda", "Annemette A. Damgaard", true));
        Employee laha = new Employee("laha", "Lars Hansen", true);
        employeeRepository.save(laha);
        Employee alla = new Employee("alla", "Allan Lassen", true);
        employeeRepository.save(alla);

        Project KBHShop = projectRegistry.createProject("KBHShop"); // generates 26001
        KBHShop.assignProjectLeader(laha);

        Project DTU = projectRegistry.createProject("DTU");         // generates 26002
        DTU.assignProjectLeader(alla);
    }

    public static App getInstance() {
        if(instance == null) {
            instance = new App();
            instance.initializeUsers();
            instance.run();
        }
        return instance;
    }

    public static void resetInstanceForTests() {
        instance = null;
    }

    public List<Employee> getAvailableEmployees() {
        Map<String, Employee> employeeMap = employeeRepository.getEmployees();
        List<Employee> returnList = new ArrayList<>();

        for (Employee emp : employeeMap.values()) {

            if (emp.isAvailable()) {
                returnList.add(emp);
            }
        }
        return returnList;
    }

    public void importEmployeesFromFile(String path) {
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

    public EmployeeRepository getEmployeeRepository() {
        return employeeRepository;
    }

    // Check login
    public boolean isUserLoggedIn() {
        return loggedInUser != null;
    }

    public Employee getLoggedInUser() {
        return loggedInUser;
    }

    public ProjectRegistry getProjectRegistry() {
        return projectRegistry;
    }

    public ActivityService getActivityService() {
        return activityService;
    }

    public InMemoryTimeEntryRepository getTimeEntryRepository() {
        return timeEntryRepository;
    }

    public void testMethod() {
        System.out.println("This is a testmethod from App.java\nThis means that the UI and app talks together");
    }

    public Report generateReport(String projectId) {
        Project project = projectRegistry.getProjectById(projectId);
        if (project == null) {
            throw new IllegalArgumentException("Project not found: " + projectId);
    }
        List<Activity> activities = activityRepository.findByProject(projectId);
        List<TimeEntry> allEntries = timeEntryRepository.findAll();

        Set<Summary> summaries = new HashSet<>();
        double totalHoursUsed = 0;

        for (Activity activity : activities) {
            double activityHours = allEntries.stream()
                .filter(e -> e.getActivity().getId().equals(activity.getId()))
                .mapToDouble(TimeEntry::getHoursWorked)
                .sum();
            totalHoursUsed += activityHours;
            summaries.add(new Summary(activityHours, activity.getName()));
        }

        int remainingHours = Math.max(0, project.getExpectedHours() - (int) totalHoursUsed);
        return new Report(totalHoursUsed, summaries, remainingHours);
    }
}
