package ui;

import appLogic.App;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainView {

    private Scene scene;
    private App app;
    private String username;

    public MainView(Scene scene) {
        this.scene = scene;
        this.app = App.getInstance();
        this.username = app.getAppContext().getLoggedInUser().getName();
    }

    public Parent getView() {
        VBox root = new VBox(15);
        Stage stage = (Stage)scene.getWindow();
        if (stage != null) {
            stage.setTitle("Time and project management - logged in as " + username);
        }
        root.setAlignment(Pos.CENTER);

        Button logoutBtn = new Button("Log out");
        Button projectsBtn = new Button("Show projects");
        Button employeeBtn = new Button("Create Employee");
        Label loggedInUser = new Label("Logged in as " + app.getLoggedInUser().getName());
        Button showActivitiesBtn = new Button("Show activities");

        // ActionEvents
        employeeBtn.setOnAction(e -> scene.setRoot(new CreateEmployeeView(scene).getView()));
        logoutBtn.setOnAction(e -> {
            if (stage != null) {
                stage.setTitle("Time and project management");
            }
            scene.setRoot(new LoginView(scene).getView());
        });
        projectsBtn.setOnAction(e -> scene.setRoot(new ChooseProjectView(scene).getView()));
        showActivitiesBtn.setOnAction(e -> scene.setRoot(new ShowActivitiesView(scene).getView()));

        root.getChildren().addAll(loggedInUser, projectsBtn, employeeBtn, showActivitiesBtn, logoutBtn);

        return root;
    }
}