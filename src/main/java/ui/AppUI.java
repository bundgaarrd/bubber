package ui;

import io.cucumber.core.cli.Main;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class AppUI extends Application{

    private static Scene scene;

    public static void main(String[] args) {
        System.out.println("Maven rokcs!");
        launch();
    }

    public void start(Stage stage) throws IOException {
        VBox root = new VBox();
        scene = new Scene(root, 860, 640);
        scene.setRoot(new MainView(scene).getView());
        stage.setScene(scene);
        stage.setTitle("Timeregistrering");
        stage.show();
    }
}
