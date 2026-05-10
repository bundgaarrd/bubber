package ui;

import appLogic.App;
import appLogic.TimeEntry;
import appLogic.activity.ActivityRepository;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import appLogic.App;
import appLogic.AppContext;
import appLogic.employee.Employee;
import appLogic.employee.EmployeeRepository;
import appLogic.project.Project;
import appLogic.project.ProjectRegistry;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class ShowActivitiesView {

    private Scene scene;
    private App app;
    private AppContext appContext;
    private ObservableList<TimeEntry> entries = FXCollections.observableArrayList();
    private ActivityRepository activityRepository;

    public ShowActivitiesView(Scene scene) {
        this.scene = scene;
        this.app = App.getInstance();
        this.appContext = app.getAppContext();
    }

    public Parent getView() {
        BorderPane root = new BorderPane();
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(15));
        grid.setVgap(10);
        grid.setHgap(10);

        // Information in grid
        Label message = new Label("Showing activies for user: " + appContext.getLoggedInUser().getName());
        grid.add(message,1,1);

        Button backBtn = new Button("Back");
        grid.add(backBtn, 0, 1);




        // Columns
        TableColumn<TimeEntry, String> activityNameCol = new TableColumn<>("Activity name");
        activityNameCol.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getActivity().getName()));



        TableView<TimeEntry> tableData = new TableView<>();
        tableData.setItems(entries);
        tableData.getColumns().addAll(activityNameCol);

        // Event handlers
        backBtn.setOnAction(e -> {
            MainView mainView = new MainView(scene);
            scene.setRoot(mainView.getView());
        });



        // TODO: Set columns for entries



        root.setTop(grid);
        root.setCenter(tableData);
        return root;
    }
}
