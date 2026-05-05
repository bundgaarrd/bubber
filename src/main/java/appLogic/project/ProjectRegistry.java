package appLogic.project;

import java.util.HashSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public final class ProjectRegistry {
    private final Set<Project> projects = new HashSet<>();
    private final ProjectIdGenerator idGenerator = new ProjectIdGenerator();

    public Project createProject(String name) {
        String newId = idGenerator.generateId();
        Project project = new Project(newId, name);
        register(project);
        return project;
    }

    /**
     * Find a project based on project name.
     * @param name The name of the project to find
     * @return Project matching name, if none found it returns null
     */
    public Project lookup(String name) {
        for (Project project : projects) {
            if(project.getProjectName().equals(name)) {
                return project;
            }
        }

        return null;
    }

    /**
     * Register a project in the registry, if a project with the same name already exists it throws an exception.
     * @param project project to register
     */

    public void register(Project project) {
        if(lookup(project.getProjectName()) != null) {
            throw new IllegalStateException("Project with the same name already exists.");
        }
        projects.add(project);
    }

    public void deleteProjectById(String id) {
        projects.removeIf(project -> project.getProjectID().equals(id));
    }

    public void deleteProjectByName(String name) {
        Set<Project> toRemove = new HashSet<>();
        for(Project project : projects) {
            if(project.getProjectName().equals(name)) {
                toRemove.add(project);
            }
        }
        if(toRemove.isEmpty()) throw new IllegalStateException("No project with the given name exists.");
        else projects.removeAll(toRemove);
    }

    public Project getProjectById(String id) {
        for (Project project : projects) {
            if (project.getProjectID().equals(id)) {
                return project;
            }
        }
        return null;
    }

    public Project getProjectByName(String name) {
        return lookup(name);
    }

    public List<Project> getAllProjects() {
        return new ArrayList<>(projects);
    }
}
