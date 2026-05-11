package appLogic; // Lavet af Andreas (s244970) og Lea (s245072)

import appLogic.activity.ActivityService;
import appLogic.employee.Employee;
import appLogic.employee.EmployeeRepository;
import appLogic.employee.InMemoryTimeEntryRepository;
import appLogic.project.ProjectRegistry;
import appLogic.report.Report;

public class App {
    private static App instance;
    private AppContext appContext;

    public static void main(String[] args) { // Has to be run from mvn javafx:run
        System.out.println("Starting the application ...");
        instance = getInstance();
    }

    private App() {
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

    public AppContext getAppContext() {
        return appContext;
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
        return appContext.getTimeEntryRepository();
    }

    public Report getReport(String projectId) {
        return appContext.getReportService().generateReport(projectId);
    }
}
