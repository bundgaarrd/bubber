package ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class AppUI extends Application {

    private static Scene scene;

    public static void main(String[] args) {
        launch();
    }

    public void start(Stage stage) throws IOException {
        VBox root = new VBox();
        scene = new Scene(root, 860, 640);
        scene.setRoot(new LoginView(scene).getView());
        stage.setScene(scene);
        stage.setTitle("Timeregistrering");
        stage.show();
    }
}
