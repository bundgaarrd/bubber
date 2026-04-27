package ui;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MainView {

    private Scene scene;

    public MainView(Scene scene) {
        this.scene = scene;
    }

    public Parent getView() {
        BorderPane root = new BorderPane();
        Button btn = new Button("Gå til registrering");
        RegisterTimeView timeView = new RegisterTimeView(scene);
        btn.setOnAction(e -> scene.setRoot(timeView.getView()));
        VBox center = new VBox(btn);
        
        center.setAlignment(Pos.CENTER);
        root.setCenter(center);

        return root;
    }
}
