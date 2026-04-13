package appLogic;

import java.util.Set;

public class Project {

//    public static void main(String[] args) {
//
//    }

    private String projectID;
    private String name;

    private Integer expectedHours;
    private boolean hasCustomer;
    private boolean isCompleted = false;
    private Employee projectLeader;

    private Set<Activity> activities;



    // Constructor
    public Project(String name, Integer expectedHours) {
        this.name = name;
        this.expectedHours = expectedHours;
    }

}
