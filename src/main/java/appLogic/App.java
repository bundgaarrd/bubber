package appLogic;

import appLogic.activity.ActivityService;
import appLogic.employee.Employee;
import appLogic.employee.EmployeeRepository;
import appLogic.employee.InMemoryTimeEntryRepository;
import appLogic.project.Project;
import appLogic.project.ProjectRegistry;

import java.util.*;

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
        return appContext.getTimeEntryRepository();
    }

    public Report generateReport(String projectId) {
        Project project = getProjectRegistry().getProjectById(projectId);
        if (project == null) {
            throw new IllegalArgumentException("Project not found: " + projectId);
    }
        List<Activity> activities = getActivityService().findByProject(projectId);
        List<TimeEntry> allEntries = getTimeEntryRepository().findAll();

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
