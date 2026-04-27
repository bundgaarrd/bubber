package ui;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.control.*;

public class RegisterTimeView {

    private Scene scene;

    public RegisterTimeView(Scene scene) {
        this.scene = scene;
    }

    public Parent getView() {
        VBox root = new VBox();
        return root;
    }

}
