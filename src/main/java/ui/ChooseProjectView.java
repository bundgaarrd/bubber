package ui;

import java.util.ArrayList;
import java.util.List;

import appLogic.App;
import appLogic.AppContext;
import appLogic.employee.Employee;
import appLogic.employee.EmployeeRepository;
import appLogic.project.Project;
import appLogic.project.ProjectRegistry;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class ChooseProjectView {

    private Scene scene;
    private ObservableList<Project> projectData = FXCollections.observableArrayList();
    private App app;
    private AppContext appContext;

    public ChooseProjectView(Scene scene) {
        this.scene = scene;
        this.app = App.getInstance();
        this.appContext = app.getAppContext();
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
                new javafx.beans.property.SimpleStringProperty(String.valueOf(data.getValue().getExpectedHours())));

        TableColumn<Project, String> projectLeaderCol = new TableColumn<>("Project leader");
        projectLeaderCol.setCellValueFactory(data -> {
            Employee leader = data.getValue().getProjectLeader();
            // Ternery statement, if leader is not null set it to project leader, else "(none)"
            String name = (leader != null) ? leader.getName() : "(none)";
            return new javafx.beans.property.SimpleStringProperty(name);
        });

        tableData.getColumns().addAll(nameCol, idCol, hourCol, projectLeaderCol);

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

            Label errorLabel = new Label("Error adding project");
            errorLabel.setVisible(false);

            TextField nameField = new TextField();
            nameField.setPromptText("Project name");

            TextField expectedHoursField = new TextField();
            expectedHoursField.setPromptText("Expected hours (e.g. 40.5)");

            // Get employee names as a string
            List<String> allEmployees = new ArrayList<>();
            allEmployees.add("(none)");
            appContext.getEmployeeRepository().findAll().stream().map(Employee::getName).forEach(allEmployees::add);

            ChoiceBox<String> projectLeaderChoice = new ChoiceBox<>();

            projectLeaderChoice.getItems().addAll(allEmployees);
            projectLeaderChoice.setValue(allEmployees.getFirst());

            VBox content = new VBox(20,
                    new Label("Project name:"), nameField,
                    new Label("Expected hours:"), expectedHoursField,
                    new Label("Choose project leader:"), projectLeaderChoice,
                    errorLabel
            );
            dialog.getDialogPane().setContent(content);
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

            // Added input validation fields in createproject dialog
            Button okButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
            okButton.addEventFilter(ActionEvent.ACTION, event -> {
                boolean hasError = false;

                if (nameField.getText().isBlank()) {
                    errorLabel.setText("Project name cannot be empty.");
                    hasError = true;
                } else {
                    try {
                        Double.parseDouble(expectedHoursField.getText().replace(",", "."));
                    } catch (NumberFormatException ex) {
                        errorLabel.setText("Expected hours must be a valid number.");
                        hasError = true;
                    }
                }

                if (hasError) {
                    errorLabel.setVisible(true);
                    event.consume();
                }
            });

            dialog.showAndWait().ifPresent(result -> {
                if (result == ButtonType.OK) {
                    ProjectRegistry projectRegistry = appContext.getProjectRegistry();
                    Project project = projectRegistry.createProject(nameField.getText());
                    EmployeeRepository employeeRepository = appContext.getEmployeeRepository();
                    String projectLeaderString = projectLeaderChoice.getValue();
                    double expectedHours = Double.parseDouble(expectedHoursField.getText().replace(",", "."));
                    project.assignProjectLeader(employeeRepository.findByName(projectLeaderString));
                    project.setExpectedHours(expectedHours);
                    projectData.add(project);
                }
            });
        });

        tableData.setOnMouseClicked(e -> {
            Project selected = tableData.getSelectionModel().getSelectedItem();

            if (e.getClickCount() == 2 && selected != null) {
                RegisterTimeView timeView = new RegisterTimeView(scene, selected); // pass project
                scene.setRoot(timeView.getView());
                }
});
        // -----End Events for interactive elements-----

        // Layout adjustments
        root.setTop(grid);
        root.setCenter(tableData);
        return root;
    }

}
