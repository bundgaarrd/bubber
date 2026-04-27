package appLogic;

import io.cucumber.core.runtime.Runtime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class App {
    private Map<String, Employee> employees;
    private Set<Project> projects;
    private boolean appActive;
    private EmployeeRepository employeeRepository;
    private Employee loggedInUser;

    public static void main(String[] args) { // Has to be run from mvn javafx:run
        System.out.println("Starting the application ...");
        App app = new App(new InMemoryEmployeeRepository());
        app.run();
    }

    private void run() {
        while (appActive) {
            System.out.println("Application started!");
            appActive = false;
            System.out.println("Quitting!");
        }
    }

    public App(EmployeeRepository repository) {
        this.employeeRepository = repository;
        this.employees = new HashMap<>();
        this.projects = new HashSet<>();
        this.appActive = true;
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
}
