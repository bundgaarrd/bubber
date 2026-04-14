package appLogic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class App {
    private Map<String, Employee> employees;
    private Set<Project> projects;
    private boolean adminLogin;

    public App() {
        this.employees = new HashMap<>();
        this.projects = new HashSet<>();
        this.adminLogin = false;
    }

    public Project Project(String name, int expectedHours) { // Create project
        Project newProject = new Project(name, expectedHours);
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

    public void importEmployeesFromFile(String path) {
    }

    public List<Project> getAllProjects() {
        return new ArrayList<>(projects);
    }

    public boolean isAdminLoggedIn() {
        return adminLogin;
    }

}
