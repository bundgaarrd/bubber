package ui; // s244970

import appLogic.App;
import appLogic.AppContext;
import appLogic.TimeEntry;
import appLogic.employee.Employee;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class EditTimeEntryView {
    private final TimeEntry timeEntry;
    private App app;
    private AppContext appContext;

    public EditTimeEntryView(TimeEntry timeEntry) {
        this.timeEntry = timeEntry;
        this.app = App.getInstance();
        this.appContext = app.getAppContext();
    }

    public Parent getView() {
        BorderPane root = new BorderPane();
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(15));
        grid.setHgap(10);
        grid.setVgap(10);

        TextField employeeField = new TextField();
        TextField activityDescField = new TextField();
        TextField activitySummaryField = new TextField();
        TextField hoursField = new TextField();

        grid.add(new Label("Employee initials:"), 0, 0);
        grid.add(employeeField, 1, 0);

        grid.add(new Label("Activity description:"), 0, 1);
        grid.add(activityDescField, 1, 1);

        grid.add(new Label("Activity summary:"), 0, 2);
        grid.add(activitySummaryField, 1, 2);

        grid.add(new Label("Hours worked:"), 0, 3);
        grid.add(hoursField, 1, 3);

        Button addButton = new Button("Update time entry");
        grid.add(addButton, 1, 4);

        Label availableEmployeesLabel = new Label("Available employees");
        grid.add(availableEmployeesLabel,2,0);

        ListView<String> availableEmployees = new ListView<>();
        grid.add(availableEmployees,2,1);

        List<Employee> employees= app.getAvailableEmployees();
        for (Employee emp : employees) {
            availableEmployees.getItems().add(emp.getName());
        }

        // Events


        root.setCenter(grid);
        return root;
    }
}