package ui; //s244813 & s244970

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

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
        root.setPadding(new Insets(20));

        Label title = new Label("Register Time — " + selectedProject.getProjectName());
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Label subtitle = new Label("Project ID: " + selectedProject.getProjectID());
        subtitle.setStyle("-fx-text-fill: #777; -fx-font-size: 12px;");

        VBox header = new VBox(2, title, subtitle);
        header.setPadding(new Insets(0, 0, 15, 0));
        root.setTop(header);


        List<String> initials = new ArrayList<>();
        employeeRepository.getAllAvailableEmployees().stream()
            .map(Employee::getInitials)
            .forEach(initials::add);
        ChoiceBox<String> employeeBox = new ChoiceBox<>();
        employeeBox.getItems().addAll(initials);
        if (!initials.isEmpty()) employeeBox.setValue(initials.get(0));

        TextField descField    = new TextField(); descField.setPromptText("e.g. Implement login");
        TextField summaryField = new TextField(); summaryField.setPromptText("Short summary");
        DatePicker startDate   = new DatePicker(LocalDate.now());
        DatePicker endDate     = new DatePicker(LocalDate.now());
        TextField hoursField   = new TextField(); hoursField.setPromptText("e.g. 2.5");
        hoursField.setPrefWidth(70);

        HBox row = new HBox(12,
            field("Employee",    employeeBox),
            field("Description", descField),
            field("Summary",     summaryField),
            field("Start date",  startDate),
            field("End date",    endDate),
            field("Hours",       hoursField)
        );

        HBox.setHgrow(descField,    Priority.ALWAYS);
        HBox.setHgrow(summaryField, Priority.ALWAYS);
        descField.setMaxWidth(Double.MAX_VALUE);
        summaryField.setMaxWidth(Double.MAX_VALUE);

        Button addButton  = new Button("Add Entry");
        addButton.setStyle("-fx-base: #4f8ef7; -fx-text-fill: white; -fx-font-weight: bold;");
        Button backButton = new Button("Back");

        HBox actions = new HBox(8, addButton, backButton);
        actions.setAlignment(Pos.CENTER_RIGHT);

        Label messageLabel = new Label();
        messageLabel.setStyle("-fx-text-fill: #c94444;");

        VBox formBlock = new VBox(10, row, actions, messageLabel);
        formBlock.setPadding(new Insets(12));
        formBlock.setStyle(
            "-fx-background-color: #f5f5f7;" +
            "-fx-background-radius: 8;" +
            "-fx-border-color: #e0e0e6;" +
            "-fx-border-radius: 8;"
        );

        TableView<TimeEntry> table = new TableView<>();
        table.setItems(tableData);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setPlaceholder(new Label("No time entries for this project yet"));

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

        TableColumn<TimeEntry, Void> colDelete = new TableColumn<>("");
        colDelete.setMinWidth(80);
        colDelete.setMaxWidth(80);
        colDelete.setCellFactory(col -> new TableCell<>() {
            private final Button btn = new Button("Delete");
            {
                btn.setStyle("-fx-text-fill: #c94444;");
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

        Label hint = new Label("Double-click a row to edit");
        hint.setStyle("-fx-text-fill: #888; -fx-font-size: 11px;");

        VBox tableBlock = new VBox(8, table, hint);
        VBox.setVgrow(table, Priority.ALWAYS);
        VBox.setVgrow(tableBlock, Priority.ALWAYS);

        VBox center = new VBox(15, formBlock, tableBlock);
        VBox.setVgrow(tableBlock, Priority.ALWAYS);
        root.setCenter(center);

        addButton.setOnAction(e -> {
            messageLabel.setStyle("-fx-text-fill: #c94444;");

            Employee employee = employeeRepository.findByInitials(employeeBox.getValue());
            if (employee == null) { messageLabel.setText("Employee not found"); return; }
            if (descField.getText().isBlank()) { messageLabel.setText("Description required"); return; }
            if (startDate.getValue() == null || endDate.getValue() == null) {
                messageLabel.setText("Pick start and end dates"); return;
            }
            if (endDate.getValue().isBefore(startDate.getValue())) {
                messageLabel.setText("End date cannot be before start date"); return;
            }

            double hours;
            try {
                hours = Double.parseDouble(hoursField.getText().trim());
                if (hours <= 0) throw new NumberFormatException();
            } catch (NumberFormatException ex) {
                messageLabel.setText("Enter valid hours");
                return;
            }

            WeekFields wf = WeekFields.of(Locale.getDefault());
            int startWeek = startDate.getValue().get(wf.weekOfWeekBasedYear());
            int endWeek   = endDate.getValue().get(wf.weekOfWeekBasedYear());
            int startYear = startDate.getValue().getYear();
            int endYear   = endDate.getValue().getYear();

            Activity activity = activityService.createWorkActivity(new CreateWorkActivity(
                selectedProject.getProjectID(),
                employee.getInitials() + "-" + descField.getText(),
                descField.getText(),
                summaryField.getText(),
                startWeek, endWeek, startYear, endYear, 5
            ));

            TimeEntry entry = activityService.registerWork(
                activity.getId(), employee, LocalDateTime.now(), hours
            );
            tableData.add(entry);

            messageLabel.setStyle("-fx-text-fill: #2e8b57;");
            messageLabel.setText("Entry saved!");

            descField.clear(); summaryField.clear(); hoursField.clear();
            startDate.setValue(LocalDate.now());
            endDate.setValue(LocalDate.now());
        });

        backButton.setOnAction(e -> {
            ChooseProjectView projectView = new ChooseProjectView(scene);
            scene.setRoot(projectView.getView());
        });

        table.setOnMouseClicked(e -> {
            TimeEntry selected = table.getSelectionModel().getSelectedItem();
            if (e.getClickCount() == 2 && selected != null) {
                new EditTimeEntryView(selected).show();
            }
        });

        return root;
    }

    private VBox field(String label, javafx.scene.Node input) {
        Label l = new Label(label);
        l.setStyle("-fx-font-size: 11px; -fx-text-fill: #555; -fx-font-weight: bold;");
        VBox box = new VBox(4, l, input);
        return box;
    }
}