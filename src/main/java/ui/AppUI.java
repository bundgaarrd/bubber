package ui; 

import java.io.IOException;

import appLogic.App;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AppUI extends Application{

    private static Scene scene;
    private App app;

    public static void main(String[] args) {
        launch();
    }

    public void start(Stage stage) throws IOException {
        app = App.getInstance();
        VBox root = new VBox();
        scene = new Scene(root, 860, 640);
        scene.setRoot(new LoginView(scene).getView());
        stage.setScene(scene);
        stage.setTitle("Timeregistrering");
        stage.show();
    }
}
