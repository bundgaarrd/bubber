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
        VBox root = new VBox();
        Button btn = new Button("Gå til registrering");
        RegisterTimeView timeView = new RegisterTimeView(scene);
        btn.setOnAction(e -> scene.setRoot(timeView.getView()));
        root.getChildren().add(btn);
        return root;
    }
}
