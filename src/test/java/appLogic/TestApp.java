package appLogic;

import io.cucumber.java.en_old.Ac;

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
        this.app = new App();
        app.setAdminLoggedIn(true);
    }

    public App getApp() {
        return app;
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
}
