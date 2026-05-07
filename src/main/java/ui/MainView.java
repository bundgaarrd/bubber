package ui; 

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class MainView {

    private Scene scene;

    public MainView(Scene scene) {
        this.scene = scene;
    }

    public Parent getView() {
        VBox root = new VBox(15);
        root.setAlignment(Pos.CENTER);

        Button logoutBtn = new Button("Log out");
        Button projectsBtn = new Button("Show projects");
        Button employeeBtn = new Button("Create Employee");
        Label loggedInUser = new Label("Logged in as <user>" );

        // ActionEvents
        employeeBtn.setOnAction(e -> scene.setRoot(new CreateEmployeeView(scene).getView()));
        logoutBtn.setOnAction(e -> scene.setRoot(new LoginView(scene).getView()));
        projectsBtn.setOnAction(e -> scene.setRoot(new ChooseProjectView(scene).getView()));

        root.getChildren().addAll(loggedInUser, projectsBtn, employeeBtn, logoutBtn);

        return root;
    }
}