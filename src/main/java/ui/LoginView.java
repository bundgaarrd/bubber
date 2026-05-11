package ui; // Lavet af Andreas (s244970)

import appLogic.App;
import appLogic.AppContext;
import javafx.geometry.Pos;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;


public class LoginView {

    private final Scene scene;

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

        Runnable loginAction = () -> {
            String initials = initialsField.getText().trim();

            try {
                appContext.login(initials);
                MainView mainView = new MainView(scene);
                scene.setRoot(mainView.getView());
            } catch (IllegalArgumentException | IllegalStateException exception) {
                errorLabel.setText(exception.getMessage());
                errorLabel.setVisible(true);
            }
        };

        loginBtn.setOnAction(e -> loginAction.run());
        initialsField.addEventHandler(KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() == KeyCode.ENTER) {
                loginAction.run();
                e.consume();
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
