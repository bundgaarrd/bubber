package ui; // Lavet af Andreas (s244970) og Valdemar (s246575)

import appLogic.App;
import appLogic.AppContext;
import appLogic.TimeEntry;
import appLogic.activity.ActivityService;
import appLogic.employee.Employee;
import appLogic.employee.EmployeeRepository;
import appLogic.employee.InMemoryEmployeeRepository;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class EditTimeEntryView {
    private final TimeEntry timeEntry;
    private App app;
    private AppContext appContext;
    private ActivityService activityService;
    private EmployeeRepository employeeRepository;

    public EditTimeEntryView(TimeEntry timeEntry) {
        this.timeEntry = timeEntry;
        this.app = App.getInstance();
        this.appContext = app.getAppContext();
        this.activityService = app.getActivityService();
        this.employeeRepository = app.getEmployeeRepository();
    }

    public void show() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Edit time entry");
        dialog.setHeaderText(null);
        dialog.getDialogPane().setMinWidth(300);

        // Get employee names as a string
        List<String> allEmployees = new ArrayList<>();
        appContext.getEmployeeRepository().findAll().stream().map(Employee::getName).sorted().forEach(allEmployees::add);

        ChoiceBox<String> employeeChoice = new ChoiceBox<>();
        employeeChoice.getItems().addAll(allEmployees);
        employeeChoice.setValue(timeEntry.getEmployee().getName());

        TextField activityField = new TextField(timeEntry.getActivity().getDescription());
        activityField.setEditable(false);
        TextField hoursField = new TextField(String.valueOf(timeEntry.getHoursWorked()));

        VBox content = new VBox(10,
                new Label("Employee:"), employeeChoice,
                new Label("Activity:"), activityField,
                new Label("Hours:"), hoursField
        );
        dialog.getDialogPane().setContent(content);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.showAndWait().ifPresent(result -> {
            if (result == ButtonType.OK) {
                Employee emp = appContext.getEmployeeRepository().findByName(employeeChoice.getValue());
                if (emp != null) {
                    Employee previous = timeEntry.getEmployee();
                    if (previous != null) {
                        previous.removeEntry(timeEntry);
                    }
                    timeEntry.setEmployee(emp);
                    emp.addEntry(timeEntry);
                }
                timeEntry.setHoursWorked(Double.parseDouble(hoursField.getText()));

            }
        });
    }
}