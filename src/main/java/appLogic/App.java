package appLogic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import appLogic.activity.ActivityService;
import appLogic.employee.Employee;
import appLogic.employee.EmployeeRepository;
import appLogic.employee.InMemoryTimeEntryRepository;
import appLogic.project.ProjectRegistry;
import appLogic.report.Report;
import appLogic.report.ReportService;

public class App {
    private static App instance;
    private AppContext appContext;
    private final ReportService reportService;

    public static void main(String[] args) { // Has to be run from mvn javafx:run
        System.out.println("Starting the application ...");
        instance = getInstance();
    }

    private App() {
        appContext = AppContext.initialize();

        this.reportService = new ReportService(
                appContext.getProjectRegistry(),
                appContext.getActivityRepository(),
                appContext.getTimeEntryRepository()
        );
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
        return reportService.generateReport(projectId);
    }
}
