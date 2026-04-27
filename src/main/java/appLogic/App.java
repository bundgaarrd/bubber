package appLogic;

import java.util.*;

public class App {
    private static App instance;

    private Map<String, Employee> employees;
    private Set<Project> projects;
    private boolean appActive;
    private EmployeeRepository employeeRepository;
    private Employee loggedInUser;

    public static void main(String[] args) { // Has to be run from mvn javafx:run
        System.out.println("Starting the application ...");
        instance = new App();
        instance.run();
    }

    private void run() {
        while (appActive) {
            System.out.println("Application started!");
            appActive = false;
            System.out.println("Quitting!");
        }
    }

    public App() {
        this.employeeRepository = new InMemoryEmployeeRepository();
        this.employees = new HashMap<>();
        this.projects = new HashSet<>();
        this.appActive = true;
    }

    public static App getInstance() {
        return instance;
    }

    public Project createProject(String name) { // Create project
        Project newProject = new Project(name);
        // Check if a project with the same name already exists
        projects.stream().filter(predicate -> predicate.getProjectID().equals(newProject.getProjectID())).findFirst().ifPresent(project -> {
            throw new IllegalStateException("A project with the name " + name + " already exists in the system.");
        });
        this.projects.add(newProject);
        return newProject;
    }

    public void deleteProject(String id) {
        projects.removeIf(project -> project.getProjectID().equals(id)); // Antager at Project har en projectID() metode
    }

    public List<Employee> getAvailableEmployees(int week, int year) {
        return null; // placeholder 
        // Hvordan skal det laves?
        // Hvornår er en employee "available"? Under hvilket antal timer på activities?
    }

    public Project getProjectById(String id) {
        for (Project p : projects) {
            if (p.getProjectID().equals(id)) {
                return p;
            }
        }
        return null;
    }

    public Project getProjectByName(String name) {
        for (Project p : projects) {
            if (p.getProjectName().equals(name)) {
                return p;
            }
        }
        return null;
    }

    public void importEmployeesFromFile(String path) {
    }

    public List<Project> getAllProjects() {
        return new ArrayList<>(projects);
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

    // Check login
    public boolean isUserLoggedIn() {
        return loggedInUser != null;
    }

    public boolean isAdminLoggedIn() {
        return loggedInUser != null;
    }

    public Employee getLoggedInUser() {
        return loggedInUser;
    }

    public Set<Activity> getAllActivities() {
        if(!isAdminLoggedIn()) throw new IllegalStateException("Only admin can access activities.");
        List<Project> projects = getAllProjects();
        Set<Activity> activities = new HashSet<>();
        for(Project project : projects) {
            activities.addAll(project.getActivities());
        }

        return activities;
    }
}
