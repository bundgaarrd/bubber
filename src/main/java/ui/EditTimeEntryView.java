package ui; // s244970

import appLogic.App;
import appLogic.AppContext;
import appLogic.TimeEntry;
import appLogic.employee.Employee;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class EditTimeEntryView {
    private final TimeEntry timeEntry;
    private App app;
    private AppContext appContext;

    public EditTimeEntryView(TimeEntry timeEntry) {
        this.timeEntry = timeEntry;
        this.app = App.getInstance();
        this.appContext = app.getAppContext();
    }

    public void show() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Edit time entry");
        dialog.setHeaderText(null);
        dialog.getDialogPane().setMinWidth(300);

        TextField employeeField = new TextField(timeEntry.getEmployee().getInitials());
        TextField activityField = new TextField(timeEntry.getActivity().getDescription());
        activityField.setEditable(false);
        TextField hoursField = new TextField(String.valueOf(timeEntry.getHoursWorked()));

        VBox content = new VBox(10,
                new Label("Employee:"), employeeField,
                new Label("Activity:"), activityField,
                new Label("Hours:"), hoursField
        );
        dialog.getDialogPane().setContent(content);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.showAndWait().ifPresent(result -> {
            if (result == ButtonType.OK) {
                Employee emp = appContext.getEmployeeRepository().findByInitials(employeeField.getText());
                if (emp != null) timeEntry.setEmployee(emp);
                timeEntry.setHoursWorked(Double.parseDouble(hoursField.getText()));
            }
        });
    }
}