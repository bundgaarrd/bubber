package appLogic;

import appLogic.employee.Employee;
import appLogic.project.Project;
import appLogic.project.ProjectRegistry;

public class TestApp {
    private static TestApp instance;
    private App app;
    private Project project;
    private Activity activity;

    public static TestApp getInstance() {
        if(instance == null) {
            instance = new TestApp();
        }
        return instance;
    }

    private TestApp() {
        reset();
    }

    public void reset() {
        App.resetInstanceForTests();
        this.app = App.getInstance();
        this.app.getAppContext().reset();
        this.project = null;
        this.activity = null;
    }

    public App getApp() {
        return app;
    }

    public ProjectRegistry getProjectRegistry() {
        return app.getProjectRegistry();
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Project getProject() {
        return project;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Activity getActivity() {
        return activity;
    }

    public Employee getEmployeeByInitials(String initials) {
        return app.getEmployeeRepository().findByInitials(initials);
    }
}
