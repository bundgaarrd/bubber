package appLogic;

import appLogic.activity.ActivityService;
import appLogic.employee.Employee;
import appLogic.employee.EmployeeRepository;
import appLogic.employee.InMemoryTimeEntryRepository;
import appLogic.project.Project;
import appLogic.project.ProjectRegistry;
import appLogic.report.Report;
import appLogic.report.ReportService;
import appLogic.activity.ActivityRepository;

import java.util.*;

public class App {
    private static App instance;
    private AppContext appContext;

    private final ProjectRegistry projectRegistry;
    private final EmployeeRepository employeeRepository;
    private final InMemoryTimeEntryRepository timeEntryRepository;
    private final ActivityService activityService;
    private final ActivityRepository activityRepository;
    private Employee loggedInUser;
    private final ReportService reportService;

    public static void main(String[] args) { // Has to be run from mvn javafx:run
        System.out.println("Starting the application ...");
        instance = getInstance();
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

        this.reportService = new ReportService(
                this.projectRegistry,
                this.activityRepository,
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
        appContext = AppContext.initialize();
    }

    public static App getInstance() {
        if(instance == null) {
            instance = new App();
        }

        return instance;
    }

    public static void resetInstanceForTests() {
        instance = null;
    }

    public List<Employee> getAvailableEmployees() {
        Map<String, Employee> employeeMap = appContext.getEmployeeRepository().getEmployees();
        List<Employee> returnList = new ArrayList<>();

        for (Employee emp : employeeMap.values()) {

            if (emp.isAvailable()) {
                returnList.add(emp);
            }
        }
        return returnList;
    }

    public AppContext getAppContext() {
        return appContext;
    }

    public void login(String initials) {
        appContext.login(initials);
    }

    public EmployeeRepository getEmployeeRepository() {
        return appContext.getEmployeeRepository();
    }

    // Check login
    public boolean isUserLoggedIn() {
        return appContext.getLoggedInUser() != null;
    }

    public Employee getLoggedInUser() {
        return appContext.getLoggedInUser();
    }

    public ProjectRegistry getProjectRegistry() {
        return appContext.getProjectRegistry();
    }

    public ActivityService getActivityService() {
        return appContext.getActivityService();
    }

    public InMemoryTimeEntryRepository getTimeEntryRepository() {
        return timeEntryRepository;
    }

    public Report getReport(String projectId) {
        return reportService.generateReport(projectId);
    }

    public void testMethod() {
        System.out.println("This is a testmethod from App.java\nThis means that the UI and app talks together");
    }
}
