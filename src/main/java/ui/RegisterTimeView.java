package ui; //s244813 & s244970

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import appLogic.Activity;
import appLogic.AppContext;
import appLogic.employee.Employee;
import appLogic.employee.EmployeeRepository;
import appLogic.employee.InMemoryTimeEntryRepository;
import appLogic.TimeEntry;
import appLogic.activity.ActivityService;
import appLogic.activity.command.CreateWorkActivity;
import appLogic.project.Project;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.Modality;
import javafx.scene.layout.VBox;

public class RegisterTimeView {

    private final Scene scene;
    private final EmployeeRepository employeeRepository = AppContext.employeeRepository;
    private final InMemoryTimeEntryRepository timeEntryRepository = AppContext.timeEntryRepository;
    private final ActivityService activityService = AppContext.activityService;

    private final ObservableList<TimeEntry> tableData = FXCollections.observableArrayList();

    public RegisterTimeView(Scene scene) {
        this.scene = scene;
        tableData.addAll(timeEntryRepository.findAll());
    }

    public Parent getView() {

        BorderPane root = new BorderPane();

        GridPane form = new GridPane();
        form.setPadding(new Insets(15));
        form.setHgap(10);
        form.setVgap(10);

        TextField employeeField = new TextField();
        TextField activityDescField = new TextField();
        TextField activitySummaryField = new TextField();
        TextField hoursField = new TextField();

        form.add(new Label("Employee initials:"), 0, 0);
        form.add(employeeField, 1, 0);

        form.add(new Label("Activity description:"), 0, 1);
        form.add(activityDescField, 1, 1);

        form.add(new Label("Activity summary:"), 0, 2);
        form.add(activitySummaryField, 1, 2);

        form.add(new Label("Hours worked:"), 0, 3);
        form.add(hoursField, 1, 3);

        Button addButton = new Button("Register Time");
        form.add(addButton, 1, 4);

        form.add(new Label("Double click on a time entry to update details"),2,0);
        Button backButton = new Button("Back");
        form.add(backButton, 1, 5);

        root.setTop(form);

        TableView<TimeEntry> table = new TableView<>();
        table.setItems(tableData);

        TableColumn<TimeEntry, String> colEmployee = new TableColumn<>("Employee");
        colEmployee.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(
                        data.getValue().getEmployee().getInitials()
                )
        );

        TableColumn<TimeEntry, String> colActivity = new TableColumn<>("Activity");
        colActivity.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(
                        data.getValue().getActivity().getDescription()
                )
        );

        TableColumn<TimeEntry, String> colStart = new TableColumn<>("Start time");
        colStart.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(
                        String.valueOf(data.getValue().getEntryStartTime())
                )
        );

        TableColumn<TimeEntry, String> colHours = new TableColumn<>("Hours");
        colHours.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(
                        String.valueOf(data.getValue().getHoursWorked())
                )
        );

        table.getColumns().addAll(colEmployee, colActivity, colStart, colHours);
        root.setCenter(table);

        addButton.setOnAction(e -> {

            Employee employee = employeeRepository.findByInitials(employeeField.getText());

            if (employee == null) {
                System.out.println("Employee not found");
                return;
            }

            double hours;
            try {
                hours = Double.parseDouble(hoursField.getText());
            } catch (Exception ex) {
                System.out.println("Invalid hours");
                return;
            }

            Project contextProject = AppContext.projectRegistry.getProjectByName("AppContext");
            if (contextProject == null) {
                System.out.println("Project not found");
                return;
            }
            Activity activity = activityService.createWorkActivity(new CreateWorkActivity(
                    contextProject.getProjectID(),
                    employee.getInitials() + "-" + activityDescField.getText(),
                    activityDescField.getText(),
                    activitySummaryField.getText(),
                    LocalDate.now()
            ));

            TimeEntry entry = activityService.registerWork(activity.getId(), employee, LocalDateTime.now(), hours);
            tableData.add(entry);

            System.out.println("Time entry saved");
        });

        backButton.setOnAction(e -> {
            ChooseProjectView projectView = new ChooseProjectView(scene);
            scene.setRoot(projectView.getView());
        });

        // When an entry is double clicked in the table
        table.setOnMouseClicked(e -> {
            TimeEntry selected = table.getSelectionModel().getSelectedItem();

            if (e.getClickCount() == 2 && selected != null) {
                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.setTitle("Edit time entry");
                dialog.getDialogPane().setContent(new EditTimeEntryView(selected).getView());
                dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
                dialog.showAndWait();
            }
        });

        return root;
    }
}
