package ui; //s244813

import appLogic.AppContext;
import appLogic.Employee;
import appLogic.EmployeeRepository;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class CreateEmployeeView {

    private final Scene scene;
    private final EmployeeRepository employeeRepository = AppContext.employeeRepository;

    public CreateEmployeeView(Scene scene) {
        this.scene = scene;
    }

    public Parent getView() {

        BorderPane root = new BorderPane();

        GridPane form = new GridPane();
        form.setPadding(new Insets(20));
        form.setHgap(10);
        form.setVgap(10);

        TextField initialsField = new TextField();
        TextField nameField = new TextField();
        CheckBox availableBox = new CheckBox("Available");
        availableBox.setSelected(true);

        form.add(new Label("Initials:"), 0, 0);
        form.add(initialsField, 1, 0);

        form.add(new Label("Name:"), 0, 1);
        form.add(nameField, 1, 1);

        form.add(availableBox, 1, 2);

        Button saveButton = new Button("Create Employee");
        Button backButton = new Button("Back");

        Label messageLabel = new Label();

        HBox buttons = new HBox(10, saveButton, backButton);
        form.add(buttons, 1, 3);
        form.add(messageLabel, 1, 4);

        root.setCenter(form);

        saveButton.setOnAction(e -> {

            String initials = initialsField.getText().trim();
            String name = nameField.getText().trim();

            if (initials.isEmpty() || name.isEmpty()) {
                messageLabel.setText("Fill all fields");
                return;
            }

            if (employeeRepository.findByInitials(initials) != null) {
                messageLabel.setText("Employee already exists");
                return;
            }

            Employee employee = new Employee(
                    initials,
                    name,
                    availableBox.isSelected()
            );

            employeeRepository.save(employee);

            messageLabel.setText("Employee created!");

            // clear fields
            initialsField.clear();
            nameField.clear();
            availableBox.setSelected(true);
        });

        backButton.setOnAction(e -> {
            MainView mainView = new MainView(scene);
            scene.setRoot(mainView.getView());
        });

        return root;
    }
}