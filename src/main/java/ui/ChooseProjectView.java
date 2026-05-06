package ui;

import appLogic.App;
import appLogic.project.Project;
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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ChooseProjectView {

    private Scene scene;
    private ObservableList<Project> projectData = FXCollections.observableArrayList();

    public ChooseProjectView(Scene scene) {
        this.scene = scene;
    }

    public Parent getView() {
        // App calls
        App app = App.getInstance();

        BorderPane root = new BorderPane();
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(15));

        grid.setHgap(10);
        grid.setVgap(10);

        // -----Tables-----
        TableView<Project> tableView = new TableView<>();
        tableView.setItems(projectData);

        TableColumn<Project, String> nameCol = new TableColumn<>("Project name");
        nameCol.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getProjectName()));


        TableColumn<Project, String> idCol = new TableColumn<>("Project ID");
        idCol.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getProjectID()));

        tableView.getColumns().addAll(nameCol, idCol);

        // For later
        // projectData.addAll(ProjectRepository.getAllProjects())

        //-----End tables

        Button backBtn = new Button("Back");
        grid.add(backBtn, 1, 0);

        // ActionEvents for interactive elements
        backBtn.setOnAction(e -> {
            MainView mainView = new MainView(scene);
            scene.setRoot(mainView.getView());
        });

        // Layout adjustments
        root.setTop(grid);
        root.setCenter(tableView);
        return root;
    }

}
