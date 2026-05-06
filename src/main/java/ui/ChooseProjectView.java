package ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class ChooseProjectView {

    private Scene scene;

    public ChooseProjectView(Scene scene) {
        this.scene = scene;
    }

    public Parent getView() {
        BorderPane root = new BorderPane();
        GridPane grid = new GridPane();

        Button backBtn = new Button("Back");

        grid.setPadding(new Insets(15));
        grid.setHgap(10);
        grid.setVgap(10);
        root.setCenter(grid);

        backBtn.setOnAction(e -> {
            MainView mainView = new MainView(scene);
            scene.setRoot(mainView.getView());
        });

        grid.add(new Label("This is a test label"),0,0);
        grid.add(backBtn, 1, 0);

        return root;
    }

}
