package ui;

import appLogic.App;
import appLogic.AppContext;
import appLogic.activity.ActivityRepository;
import appLogic.activity.impl.Activity;
import appLogic.employee.Employee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import java.util.Set;

public class ShowActivitiesView {

    private Scene scene;
    private App app;
    private AppContext appContext;
    private ObservableList<Activity> entries = FXCollections.observableArrayList();
    private ActivityRepository activityRepository;
    private Set<Activity> activitySet;
    private Employee user;
    private String username;

    public ShowActivitiesView(Scene scene) {
        this.scene = scene;
        this.app = App.getInstance();
        this.appContext = app.getAppContext();
        this.user = appContext.getLoggedInUser();
        this.username = this.user.getName();
        this.activitySet = this.user.getActivities();
        this.entries.addAll(activitySet);
    }

    public Parent getView() {
        BorderPane root = new BorderPane();
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(15));
        grid.setVgap(10);
        grid.setHgap(10);

        // Information in grid
        Label message = new Label("Showing activies for user: " + username);
        grid.add(message,1,1);

        Button backBtn = new Button("Back");
        grid.add(backBtn, 0, 1);


        // Columns
        TableColumn<Activity, String> activityNameCol = new TableColumn<>("Activity name");
        activityNameCol.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getDescription()));

        TableView<Activity> tableData = new TableView<>();
        tableData.setItems(entries);
        tableData.getColumns().addAll(activityNameCol);

        // Event handlers
        backBtn.setOnAction(e -> {
            MainView mainView = new MainView(scene);
            scene.setRoot(mainView.getView());
        });



        root.setTop(grid);
        root.setCenter(tableData);
        return root;
    }
}
