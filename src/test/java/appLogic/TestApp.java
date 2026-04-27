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
        EmployeeRepository repo = new InMemoryEmployeeRepository();
    
        repo.save(new Employee("huba")); 
        
        this.app = new App(repo);
}   

    public App getApp() {
        return app;
    }
}
