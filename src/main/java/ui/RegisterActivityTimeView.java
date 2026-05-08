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
import appLogic.activity.ActivityRepository;
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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

public class RegisterActivityTimeView {

    private final Scene scene;
    private final EmployeeRepository employeeRepository;
    private final InMemoryTimeEntryRepository timeEntryRepository;
    private final ActivityRepository activityRepository;
    private final ActivityService activityService;
    private final ProjectRegistry projectRegistry;
    private final Project selectedProject;

    private final ObservableList<TimeEntry> tableData     = FXCollections.observableArrayList();
    private final ObservableList<Activity>  activityList  = FXCollections.observableArrayList();

    public RegisterActivityTimeView(Scene scene, Project selectedProject) {
        this.scene = scene;
        this.selectedProject = selectedProject;
        AppContext appContext = App.getInstance().getAppContext();
        this.employeeRepository  = appContext.getEmployeeRepository();
        this.activityService     = appContext.getActivityService();
        this.timeEntryRepository = appContext.getTimeEntryRepository();
        this.projectRegistry     = appContext.getProjectRegistry();
        this.activityRepository  = appContext.getActivityRepository();

        activityList.addAll(activityRepository.findByProject(selectedProject.getProjectID()));

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

        ChoiceBox<Activity> activityBox = new ChoiceBox<>(activityList);
        activityBox.setConverter(new StringConverter<>() {
            @Override public String toString(Activity a) { return a == null ? "" : a.getDescription(); }
            @Override public Activity fromString(String s) { return null; }
        });
        if (!activityList.isEmpty()) activityBox.setValue(activityList.get(0));
        activityBox.setMaxWidth(Double.MAX_VALUE);

        Button newActivityBtn = new Button("+ New activity");

        List<String> name = new ArrayList<>();
        employeeRepository.getAllAvailableEmployees().stream()
            .map(Employee::getName)
            .forEach(name::add);
        ChoiceBox<String> employeeBox = new ChoiceBox<>();
        employeeBox.getItems().addAll(name);
        if (!name.isEmpty()) employeeBox.setValue(name.get(0));

        TextField hoursField = new TextField();
        hoursField.setPromptText("e.g. 2.5");
        hoursField.setPrefWidth(80);

        Button logTimeBtn = new Button("Log Time");
        logTimeBtn.setStyle("-fx-base: #4f8ef7; -fx-text-fill: white; -fx-font-weight: bold;");

        Button backButton = new Button("Back");

        Label messageLabel = new Label();
        messageLabel.setStyle("-fx-text-fill: #c94444;");

        VBox activityField = field("Activity",  activityBox);
        VBox employeeField = field("Employee",  employeeBox);
        VBox hoursFieldBox = field("Hours",     hoursField);
        VBox newActField   = field(" ",         newActivityBtn);
        VBox logField      = field(" ",         logTimeBtn);
        VBox backField     = field(" ",         backButton);

        HBox.setHgrow(activityField, Priority.ALWAYS);
        HBox row = new HBox(12, activityField, newActField, employeeField, hoursFieldBox, logField, backField);
        row.setAlignment(Pos.BOTTOM_LEFT);

        VBox formBlock = new VBox(10, row, messageLabel);
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
        table.setPlaceholder(new Label("No time logged for this project yet"));

        TableColumn<TimeEntry, String> colEmployee = new TableColumn<>("Employee");
        colEmployee.setCellValueFactory(d ->
            new javafx.beans.property.SimpleStringProperty(
                d.getValue().getEmployee().getName()));

        TableColumn<TimeEntry, String> colActivity = new TableColumn<>("Activity");
        colActivity.setCellValueFactory(d ->
            new javafx.beans.property.SimpleStringProperty(
                d.getValue().getActivity().getDescription()));

        TableColumn<TimeEntry, String> colDate = new TableColumn<>("Logged on");
        colDate.setCellValueFactory(d ->
            new javafx.beans.property.SimpleStringProperty(
                d.getValue().getEntryStartTime().toLocalDate().toString()));

        TableColumn<TimeEntry, String> colHours = new TableColumn<>("Hours");
        colHours.setCellValueFactory(d ->
            new javafx.beans.property.SimpleStringProperty(
                String.valueOf(d.getValue().getHoursWorked())));

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

        table.getColumns().addAll(colEmployee, colActivity, colDate, colHours, colDelete);

        Label hint = new Label("Double-click a row to edit");
        hint.setStyle("-fx-text-fill: #888; -fx-font-size: 11px;");

        VBox tableBlock = new VBox(8, table, hint);
        VBox.setVgrow(table, Priority.ALWAYS);

        VBox center = new VBox(15, formBlock, tableBlock);
        VBox.setVgrow(tableBlock, Priority.ALWAYS);
        root.setCenter(center);

        newActivityBtn.setOnAction(e -> openNewActivityDialog(activityBox, messageLabel));

        logTimeBtn.setOnAction(e -> {
            messageLabel.setStyle("-fx-text-fill: #c94444;");

            Activity activity = activityBox.getValue();
            if (activity == null) { messageLabel.setText("Create or select an activity first"); return; }

            Employee employee = employeeRepository.findByName(employeeBox.getValue());
            if (employee == null) { messageLabel.setText("Pick an employee"); return; }

            double hours;
            try {
                hours = Double.parseDouble(hoursField.getText().trim());
                if (hours <= 0) throw new NumberFormatException();
            } catch (NumberFormatException ex) {
                messageLabel.setText("Enter valid hours");
                return;
            }

            TimeEntry entry = activityService.registerWork(
                activity.getId(), employee, LocalDateTime.now(), hours);
            tableData.add(entry);

            messageLabel.setStyle("-fx-text-fill: #2e8b57;");
            messageLabel.setText("Time logged!");
            hoursField.clear();
        });

        backButton.setOnAction(e -> {
            ChooseProjectView projectView = new ChooseProjectView(scene);
            scene.setRoot(projectView.getView());
        });

        table.setOnMouseClicked(e -> {
            TimeEntry selected = table.getSelectionModel().getSelectedItem();
            if (e.getClickCount() == 2 && selected != null) {
                new EditTimeEntryView(selected).show();
                table.refresh();
            }
        });

        return root;
    }

    private void openNewActivityDialog(ChoiceBox<Activity> activityBox, Label messageLabel) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("New Activity");
        dialog.setHeaderText("Create activity for " + selectedProject.getProjectName());

        TextField descField    = new TextField(); descField.setPromptText("Description");
        TextField summaryField = new TextField(); summaryField.setPromptText("Summary");
        DatePicker startDate   = new DatePicker(LocalDate.now());
        DatePicker endDate     = new DatePicker(LocalDate.now());
        TextField expectedHrs  = new TextField(); expectedHrs.setPromptText("Expected hours");

        GridPane grid = new GridPane();
        grid.setHgap(10); grid.setVgap(10); grid.setPadding(new Insets(10));
        grid.add(new Label("Description:"),    0, 0); grid.add(descField,    1, 0);
        grid.add(new Label("Summary:"),        0, 1); grid.add(summaryField, 1, 1);
        grid.add(new Label("Start date:"),     0, 2); grid.add(startDate,    1, 2);
        grid.add(new Label("End date:"),       0, 3); grid.add(endDate,      1, 3);
        grid.add(new Label("Expected hours:"), 0, 4); grid.add(expectedHrs,  1, 4);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.showAndWait().ifPresent(result -> {
            if (result != ButtonType.OK) return;

            if (descField.getText().isBlank()) {
                messageLabel.setText("Description required"); return;
            }
            if (endDate.getValue().isBefore(startDate.getValue())) {
                messageLabel.setText("End date before start date"); return;
            }

            double hours;
            try {
                hours = Double.parseDouble(expectedHrs.getText().trim());
            } catch (NumberFormatException ex) {
                messageLabel.setText("Invalid expected hours"); return;
            }

            WeekFields wf = WeekFields.of(Locale.getDefault());
            int sw = startDate.getValue().get(wf.weekOfWeekBasedYear());
            int ew = endDate.getValue().get(wf.weekOfWeekBasedYear());
            int sy = startDate.getValue().getYear();
            int ey = endDate.getValue().getYear();

            Activity activity = activityService.createWorkActivity(new CreateWorkActivity(
                selectedProject.getProjectID(),
                descField.getText(),              
                descField.getText(),               
                summaryField.getText(),
                sw, ew, sy, ey, hours
            ));

            activityList.add(activity);
            activityBox.setValue(activity);
            messageLabel.setStyle("-fx-text-fill: #2e8b57;");
            messageLabel.setText("Activity created!");
        });
    }

    private VBox field(String label, javafx.scene.Node input) {
        Label l = new Label(label);
        l.setStyle("-fx-font-size: 11px; -fx-text-fill: #555; -fx-font-weight: bold;");
        return new VBox(4, l, input);
    }
}