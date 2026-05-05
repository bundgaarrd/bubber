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
        this.activityService = new DefaultActivityService(
                new InMemoryActivityRepository(),
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

        Project KBHShop = new Project("26001", "KBHShop");
        this.projectRegistry.register(KBHShop);
        KBHShop.assignProjectLeader(laha);

        Employee alla = new Employee("alla", "Allan Lassen", true);
        employeeRepository.save(alla);

        Project DTU = new Project("26002", "DTU");
        this.projectRegistry.register(DTU);
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

    public List<Employee> getAvailableEmployees(int week, int year) {
        return null; // placeholder 
        // Hvordan skal det laves?
        // Hvornår er en employee "available"? Under hvilket antal timer på activities?
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
}
