package appLogic.employee;

import appLogic.activity.impl.Activity;
import appLogic.project.Project;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.*;

public class Employee {

    private final String initials;
    private final String name;
    private boolean isAvailable;
    private final List<Activity> activities = new ArrayList<>();

    /**
     * Projects where this employee is a leader
     */
    private final Set<Project> leaderProjects = new HashSet<>();

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

    public void addActivity(Activity activity) {
        activities.add(activity);
        activity.assignEmployee(this);
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public int getActiveActivityCount(int week, int year) {
        int count = 0;

        for (Activity activity : activities) {
            if(week < activity.getStartWeek() || week > activity.getEndWeek()) continue;

            String projectReferenceId = activity.getProjectReferenceId();

            if(projectReferenceId == null) {
                count++;
                continue;
            }

            if(projectReferenceId.startsWith(String.valueOf(year))) {
                count++;
            }
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
}