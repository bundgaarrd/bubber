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
    }

    public List<TimeEntry> getEntries() {
        return entries;
    }

    public int getActiveActivityCount(int week, int year) {
        int count = 0;

        for (TimeEntry entry : entries) {

        }

        return count;
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