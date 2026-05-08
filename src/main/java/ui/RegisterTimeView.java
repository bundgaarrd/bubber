package ui; //s244813 & s244970

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import appLogic.App;
import appLogic.AppContext;
import appLogic.TimeEntry;
import appLogic.activity.ActivityService;
import appLogic.activity.command.CreateWorkActivity;
import appLogic.activity.impl.Activity;
import appLogic.employee.Employee;
import appLogic.employee.EmployeeRepository;
import appLogic.employee.InMemoryTimeEntryRepository;
import appLogic.project.Project;
import appLogic.project.ProjectRegistry;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class RegisterTimeView {

    private final Scene scene;
    private final EmployeeRepository employeeRepository;
    private final InMemoryTimeEntryRepository timeEntryRepository;
    private final ActivityService activityService;
    private final ProjectRegistry projectRegistry;
    private final Project selectedProject;

    private final ObservableList<TimeEntry> tableData = FXCollections.observableArrayList();

    public RegisterTimeView(Scene scene, Project selectedProject) {
        this.scene = scene;
        this.selectedProject = selectedProject;
        AppContext appContext = App.getInstance().getAppContext();
        this.employeeRepository = appContext.getEmployeeRepository();
        this.activityService = appContext.getActivityService();
        this.timeEntryRepository = appContext.getTimeEntryRepository();
        this.projectRegistry = appContext.getProjectRegistry();

        timeEntryRepository.findAll().stream()
            .filter(entry -> selectedProject.getProjectID()
                .equals(entry.getActivity().getProjectId()))
            .forEach(tableData::add);
    }

    public Parent getView() {

        BorderPane root = new BorderPane();

        // ── Form ──────────────────────────────────────────────────────────
        GridPane form = new GridPane();
        form.setPadding(new Insets(15));
        form.setHgap(10);
        form.setVgap(10);

        // Employee dropdown
        List<String> initials = new ArrayList<>();
        employeeRepository.getAllAvailableEmployees().stream()
            .map(Employee::getInitials)
            .forEach(initials::add);
        ChoiceBox<String> employeeBox = new ChoiceBox<>();
        employeeBox.getItems().addAll(initials);
        if (!initials.isEmpty()) employeeBox.setValue(initials.get(0));

        // Project dropdown (locked to selected project)
        ChoiceBox<String> projectBox = new ChoiceBox<>();
        projectBox.getItems().add(selectedProject.getProjectID());
        projectBox.setValue(selectedProject.getProjectID());
        projectBox.setDisable(true);

        TextField descField      = new TextField();
        TextField summaryField   = new TextField();
        TextField startWeekField = new TextField();
        TextField endWeekField   = new TextField();
        TextField startYearField = new TextField();
        TextField endYearField   = new TextField();
        TextField hoursField     = new TextField();

        Label messageLabel = new Label();

        form.add(new Label("Employee:"),   0, 0); form.add(employeeBox,    1, 0);
        form.add(new Label("Project ID:"), 0, 1); form.add(projectBox,     1, 1);
        form.add(new Label("Description:"),0, 2); form.add(descField,      1, 2);
        form.add(new Label("Summary:"),    0, 3); form.add(summaryField,   1, 3);
        form.add(new Label("Start week:"), 0, 4); form.add(startWeekField, 1, 4);
        form.add(new Label("End week:"),   0, 5); form.add(endWeekField,   1, 5);
        form.add(new Label("Start year:"), 0, 6); form.add(startYearField, 1, 6);
        form.add(new Label("End year:"),   0, 7); form.add(endYearField,   1, 7);
        form.add(new Label("Hours:"),      0, 8); form.add(hoursField,     1, 8);

        Button addButton  = new Button("Add Entry");
        Button backButton = new Button("Back");
        HBox buttons = new HBox(10, addButton, backButton);
        form.add(buttons,      1, 9);
        form.add(messageLabel, 1, 10);
        form.add(new Label("Double-click a row to edit"), 2, 0);

        root.setTop(form);

        // ── Table ─────────────────────────────────────────────────────────
        TableView<TimeEntry> table = new TableView<>();
        table.setItems(tableData);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<TimeEntry, String> colEmployee = new TableColumn<>("Employee");
        colEmployee.setCellValueFactory(d ->
            new javafx.beans.property.SimpleStringProperty(
                d.getValue().getEmployee().getInitials()
            ));

        TableColumn<TimeEntry, String> colActivity = new TableColumn<>("Description");
        colActivity.setCellValueFactory(d ->
            new javafx.beans.property.SimpleStringProperty(
                d.getValue().getActivity().getDescription()
            ));

        TableColumn<TimeEntry, String> colHours = new TableColumn<>("Hours");
        colHours.setCellValueFactory(d ->
            new javafx.beans.property.SimpleStringProperty(
                String.valueOf(d.getValue().getHoursWorked())
            ));

        TableColumn<TimeEntry, Void> colDelete = new TableColumn<>("Delete");
        colDelete.setMinWidth(80);
        colDelete.setMaxWidth(80);
        colDelete.setCellFactory(col -> new TableCell<>() {
            private final Button btn = new Button("Delete");
            {
                btn.setOnAction(e -> {
                    TimeEntry entry = getTableView().getItems().get(getIndex());
                    tableData.remove(entry);
                    timeEntryRepository.remove(entry);
                });
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
            }
        });

        table.getColumns().addAll(colEmployee, colActivity, colHours, colDelete);

        root.setCenter(table);

        // ── Events ────────────────────────────────────────────────────────
        addButton.setOnAction(e -> {
            Employee employee = employeeRepository.findByInitials(employeeBox.getValue());
            if (employee == null) { messageLabel.setText("Employee not found"); return; }

            String projectId = projectBox.getValue();
            if (projectId == null) { messageLabel.setText("Select a project"); return; }

            if (descField.getText().isBlank()) { messageLabel.setText("Description required"); return; }

            int startWeek, endWeek, startYear, endYear;
            try {
                startWeek = Integer.parseInt(startWeekField.getText().trim());
                endWeek   = Integer.parseInt(endWeekField.getText().trim());
                startYear = Integer.parseInt(startYearField.getText().trim());
                endYear   = Integer.parseInt(endYearField.getText().trim());
            } catch (NumberFormatException ex) {
                messageLabel.setText("Invalid week/year values");
                return;
            }

            double hours;
            try {
                hours = Double.parseDouble(hoursField.getText().trim());
            } catch (NumberFormatException ex) {
                messageLabel.setText("Invalid hours");
                return;
            }

            Activity activity = activityService.createWorkActivity(new CreateWorkActivity(
                projectId,
                employee.getInitials() + "-" + descField.getText(),
                descField.getText(),
                summaryField.getText(),
                startWeek,
                endWeek,
                startYear,
                endYear
            ));

            TimeEntry entry = activityService.registerWork(
                activity.getId(), employee, LocalDateTime.now(), hours
            );
            tableData.add(entry);
            messageLabel.setText("Entry saved!");
            descField.clear(); summaryField.clear(); hoursField.clear();
            startWeekField.clear(); endWeekField.clear();
            startYearField.clear(); endYearField.clear();
        });

        backButton.setOnAction(e -> {
            ChooseProjectView projectView = new ChooseProjectView(scene);
            scene.setRoot(projectView.getView());
        });

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