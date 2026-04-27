package ui;

import java.time.LocalDate;
import java.time.LocalDateTime;

import appLogic.Activity;
import appLogic.AppContext;
import appLogic.Employee;
import appLogic.EmployeeRepository;
import appLogic.InMemoryTimeEntryRepository;
import appLogic.TimeEntry;
import appLogic.WorkActivity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class RegisterTimeView {

    private final Scene scene;
    private final EmployeeRepository employeeRepository = AppContext.employeeRepository;
    private final InMemoryTimeEntryRepository timeEntryRepository = AppContext.timeEntryRepository;

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

            Activity activity = new WorkActivity(
                    activityDescField.getText(),
                    activitySummaryField.getText(),
                    LocalDate.now()
            );

            employee.addActivity(activity);

            TimeEntry entry = new TimeEntry(employee, activity);
            entry.setEntryStartTime(LocalDateTime.now());
            entry.setHoursWorked(hours);

            timeEntryRepository.save(entry);
            tableData.add(entry);

            System.out.println("Time entry saved");
        });

        return root;
    }
}