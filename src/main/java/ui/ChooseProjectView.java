package ui;

import appLogic.App;
import appLogic.employee.EmployeeRepository;
import appLogic.project.Project;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ChooseProjectView {

    private Scene scene;
    private ObservableList<Project> projectData = FXCollections.observableArrayList();
    private EmployeeRepository employeeRepository;
    private App app;

    public ChooseProjectView(Scene scene) {
        this.scene = scene;
        this.app = App.getInstance();
    }

    public Parent getView() {
        BorderPane root = new BorderPane();
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(15));

        grid.setHgap(10);
        grid.setVgap(10);

        // -----Begin tables-----
        TableView<Project> tableData = new TableView<>();
        tableData.setItems(projectData);

        TableColumn<Project, String> nameCol = new TableColumn<>("Project name");
        nameCol.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getProjectName()));


        TableColumn<Project, String> idCol = new TableColumn<>("Project ID");
        idCol.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getProjectID()));

        tableData.getColumns().addAll(nameCol, idCol);

        projectData.addAll(app.getProjectRegistry().getAllProjects());

        //-----End tables

        // -----Begin buttons------
        Button backBtn = new Button("Back");
        grid.add(backBtn, 1, 0);

        Button addProjectBtn = new Button("Add project");
        grid.add(addProjectBtn,2,0);

        // -----End buttons------

        // -----Begin ActionEvents for interactive elements-----
        backBtn.setOnAction(e -> {
            MainView mainView = new MainView(scene);
            scene.setRoot(mainView.getView());
        });

        addProjectBtn.setOnAction(e -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Add project information");
            dialog.setHeaderText(null);
            dialog.setContentText("Project name");

            dialog.showAndWait().ifPresent(name -> {
                if (!name.isBlank()) {
                    Project project = app.getProjectRegistry().createProject(name);
                    projectData.add(project);
                }
            });
        });
        // -----End ActionEvents for interactive elements-----

        // Layout adjustments
        root.setTop(grid);
        root.setCenter(tableData);
        return root;
    }

}
