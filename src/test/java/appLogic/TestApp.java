package appLogic;

public class TestApp {
    private static TestApp instance;
    private final App app;

    public static TestApp getInstance() {
        if(instance == null) {
            instance = new TestApp();
        }
        return instance;
    }

    private TestApp() {
        this.app = new App();
        app.setAdminLoggedIn(true);

    }

    public App getApp() {
        return app;
    }
}
