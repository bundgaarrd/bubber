package ui;

import appLogic.App;
import io.cucumber.core.cli.Main;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class AppUI extends Application{

    private static Scene scene;
    private App app;

    public static void main(String[] args) {
        launch();
    }

    public void start(Stage stage) throws IOException {
        app = new App();
        VBox root = new VBox();
        scene = new Scene(root, 860, 640);
        scene.setRoot(new LoginView(scene).getView());
        stage.setScene(scene);
        stage.setTitle("Timeregistrering");
        stage.show();
    }
}
