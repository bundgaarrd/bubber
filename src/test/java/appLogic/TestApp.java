package appLogic;

public class TestApp {
    private static TestApp instance;
    private App app;

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
}
