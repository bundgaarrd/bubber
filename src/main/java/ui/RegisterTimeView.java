package ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class RegisterTimeView {

    private Scene scene;

    public RegisterTimeView(Scene scene) {
        this.scene = scene;
    }

    public Parent getView() {

        BorderPane root = new BorderPane();

        GridPane form = new GridPane();
        form.setPadding(new Insets(15));
        form.setHgap(10);
        form.setVgap(10);

        TextField employeeField = new TextField();
        TextField activityField = new TextField();
        DatePicker datePicker = new DatePicker();
        TextField hoursField = new TextField();

        form.add(new Label("Employee:"), 0, 0);
        form.add(employeeField, 1, 0);

        form.add(new Label("Activity:"), 0, 1);
        form.add(activityField, 1, 1);

        form.add(new Label("Date:"), 0, 2);
        form.add(datePicker, 1, 2);

        form.add(new Label("Hours:"), 0, 3);
        form.add(hoursField, 1, 3);

        Button addButton = new Button("Register Time");
        Button backButton = new Button("Back");

        HBox buttons = new HBox(10, addButton, backButton);
        form.add(buttons, 1, 4);

        root.setTop(form);

        TableView<String> table = new TableView<>();

        TableColumn<String, String> colEmployee = new TableColumn<>("Employee");
        TableColumn<String, String> colActivity = new TableColumn<>("Activity");
        TableColumn<String, String> colDate = new TableColumn<>("Date");
        TableColumn<String, String> colHours = new TableColumn<>("Hours");

        table.getColumns().addAll(colEmployee, colActivity, colDate, colHours);

        ObservableList<String> data = FXCollections.observableArrayList();
        table.setItems(data);

        root.setCenter(table);

        addButton.setOnAction(e -> {
            // her skal jeg indsætte min funktion fra app'ens logik
            System.out.println("Register time clicked");

            // der skal gøres noget ved det her
            data.add(
                employeeField.getText() + " | " +
                activityField.getText() + " | " +
                datePicker.getValue() + " | " +
                hoursField.getText()
            );
        });

        MainView MainView = new MainView(scene);

        backButton.setOnAction(e -> {
            scene.setRoot(MainView.getView());
            System.out.println("Back clicked");
        });

        return root;
    }
}