package ui;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import javafx.scene.control.Label;


public class LoginView {

    private Scene scene;

    public LoginView(Scene scene) {
        this.scene = scene;
    }

    public Parent getView() {
        VBox root = new VBox(15);
        root.setAlignment(Pos.CENTER);

        Label label = new Label("Enter Initials:");
        Label errorLabel = new Label("User not found");
        errorLabel.setVisible(false);

        TextField initialsField = new TextField();
        initialsField.setPromptText("e.g. huba");
        initialsField.setMaxWidth(150);
        Button loginBtn = new Button("Login");

        loginBtn.setOnAction(e -> {
            String initials = initialsField.getText().trim();

            // TODO: Mangler at lave et reelt opslag på om brugeren findes. -> Artur?
            boolean userExists = initials.equals("huba");

            if (!initials.isEmpty() && userExists) {
                MainView mainView = new MainView(scene);
                scene.setRoot(mainView.getView());
            } else {
                errorLabel.setVisible(true);
            }
        });

        root.getChildren().addAll(label, initialsField, loginBtn, errorLabel);

        return root;
    }
}
