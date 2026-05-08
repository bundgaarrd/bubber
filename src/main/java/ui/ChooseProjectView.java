package ui;

import appLogic.App;
import appLogic.employee.Employee;
import appLogic.employee.EmployeeRepository;
import appLogic.project.Project;
import javafx.beans.binding.StringExpression;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javafx.scene.layout.VBox;

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

        TableColumn<Project, String> hourCol = new TableColumn<>("Expected hours");
        hourCol.setCellValueFactory(data ->
                // cumbersome
                new javafx.beans.property.SimpleStringProperty(String.valueOf(data.getValue().getExpectedHours())));

        tableData.getColumns().addAll(nameCol, idCol, hourCol);

        projectData.addAll(app.getProjectRegistry().getAllProjects());

        //-----End tables

        // -----Begin buttons and labels------
        Label message = new Label("Double click on a project to edit details");
        grid.add(message,1,0);

        Button backBtn = new Button("Back");
        grid.add(backBtn, 2, 0);

        Button addProjectBtn = new Button("Add project");
        grid.add(addProjectBtn,3,0);

        // -----End buttons------

        // -----Begin Events for interactive elements-----
        backBtn.setOnAction(e -> {
            MainView mainView = new MainView(scene);
            scene.setRoot(mainView.getView());
        });

        addProjectBtn.setOnAction(e -> {
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Add project information");
            dialog.setHeaderText(null);
            dialog.getDialogPane().setMinWidth(300);

            TextField nameField = new TextField();
            nameField.setPromptText("Project name");

            // Get employee names as a string
            List<String> allEmployees = new ArrayList<>();
            allEmployees.add("(none)");
            app.getEmployeeRepository().findAll().stream().map(Employee::getName).forEach(allEmployees::add);

            ChoiceBox<String> projectLeaderChoice = new ChoiceBox<>();

            projectLeaderChoice.getItems().addAll(allEmployees);
            projectLeaderChoice.setValue(allEmployees.getFirst());

            VBox content = new VBox(20,
                    new Label("Project name:"), nameField,
                    new Label("Choose project leader:"), projectLeaderChoice
            );
            dialog.getDialogPane().setContent(content);
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

            dialog.showAndWait().ifPresent(result -> {
                if (result == ButtonType.OK && !nameField.getText().isBlank()) {
                    Project project = app.getProjectRegistry().createProject(nameField.getText());
                    projectData.add(project);
                }
            });
        });

        tableData.setOnMouseClicked(e -> {
            Project selected = tableData.getSelectionModel().getSelectedItem();

            if (e.getClickCount() == 2) {
                if (selected != null) {
                    RegisterTimeView timeView = new RegisterTimeView(scene);
                    scene.setRoot(timeView.getView());
                }
            }
        });
        // -----End Events for interactive elements-----

        // Layout adjustments
        root.setTop(grid);
        root.setCenter(tableData);
        return root;
    }

}
