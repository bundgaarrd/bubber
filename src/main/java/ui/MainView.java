package ui; 

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class MainView {

    private Scene scene;

    public MainView(Scene scene) {
        this.scene = scene;
    }

    public Parent getView() {

        VBox root = new VBox(15);

        Button timeBtn = new Button("Register Time");
        RegisterTimeView timeView = new RegisterTimeView(scene);
        timeBtn.setOnAction(e -> scene.setRoot(timeView.getView()));

        Button employeeBtn = new Button("Create Employee");
        CreateEmployeeView employeeView = new CreateEmployeeView(scene);
        employeeBtn.setOnAction(e -> scene.setRoot(employeeView.getView()));

        root.getChildren().addAll(timeBtn, employeeBtn);

        return root;
    }
}