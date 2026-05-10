package ui;

import appLogic.App;
import appLogic.AppContext;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;


public class LoginView {

    private Scene scene;

    public LoginView(Scene scene) {
        this.scene = scene;
    }

    public Parent getView() {
        App app = App.getInstance();
        AppContext appContext = app.getAppContext();

        VBox root = new VBox(15);
        root.setAlignment(Pos.CENTER);

        Label label = new Label("Enter Initials:");
        Label errorLabel = new Label("User not found");
        errorLabel.setVisible(false);

        TextField initialsField = new TextField();
        initialsField.setPromptText("e.g. huba");
        initialsField.setMaxWidth(150);
        Button loginBtn = new Button("Login");
        Button quitBtn = new Button("Quit");

        loginBtn.setOnAction(e -> {
            String initials = initialsField.getText().trim();

            try {
                appContext.login(initials);
                MainView mainView = new MainView(scene);
                scene.setRoot(mainView.getView());
            } catch (IllegalArgumentException | IllegalStateException exception) {
                errorLabel.setText(exception.getMessage());
                errorLabel.setVisible(true);
            }
        });

        quitBtn.setOnAction(e -> {
            // Defer shutdown to avoid closing the window while the current action is still dispatching.
            javafx.application.Platform.runLater(javafx.application.Platform::exit);
        });

        root.getChildren().addAll(label, initialsField, loginBtn, quitBtn, errorLabel);

        return root;
    }
}
