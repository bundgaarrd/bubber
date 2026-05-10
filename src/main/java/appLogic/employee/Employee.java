package appLogic.employee;

import appLogic.TimeEntry;
import appLogic.project.Project;
import appLogic.activity.impl.Activity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Employee {

    private final String initials;
    private final String name;
    private boolean isAvailable;
    private final List<TimeEntry> entries = new ArrayList<>();

    /**
     * Projects where this employee is a leader
     */
    private final Set<Project> leaderProjects = new HashSet<>();

    // Activities this employee is assigned to
    private final Set<Activity> activities = new HashSet<>();

    public Employee(String initials, String name, boolean isAvailable) {
        this.initials = initials;
        this.name = name;
        this.isAvailable = isAvailable;
    }

    public String getInitials() {
        return initials;
    }

    public String getName() {
        return name;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public void addEntry(TimeEntry entry) {
        entries.add(entry);
        boolean alreadyAssigned = activities.stream()
                .anyMatch(activity -> activity.equals(entry.getActivity()));
        if (!alreadyAssigned) {
            activities.add(entry.getActivity());
        }
    }

    public void removeEntry(TimeEntry timeEntry) {
        entries.remove(timeEntry);
        // Check if the employee is still assigned to the activity through other time entries
        boolean stillAssigned = entries.stream()
                .anyMatch(e -> e.getActivity().equals(timeEntry.getActivity()));
        if (!stillAssigned) {
            activities.remove(timeEntry.getActivity());
        }
    }

    public List<TimeEntry> getEntries() {
        return entries;
    }

    public Set<Project> getLeaderProjects() {
        return leaderProjects;
    }

    public void addProjectAsLeader(Project project) {
        leaderProjects.add(project);
    }

    public void removeProjectAsLeader(Project project) {
        leaderProjects.remove(project);
    }
    // Activity schedule management

    public Set<Activity> getActivities() {
        return activities;
    }

    public void addActivity(Activity activity) {
        activities.add(activity);
    }

    public void removeActivity(Activity activity) {
        activities.remove(activity);
    }
}