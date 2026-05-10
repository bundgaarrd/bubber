package ui;

import appLogic.App;
import appLogic.AppContext;
import appLogic.FixedActivityType;
import appLogic.activity.ActivityRepository;
import appLogic.activity.ActivityService;
import appLogic.activity.command.CreateFixedActivity;
import appLogic.activity.command.CreateWorkActivity;
import appLogic.activity.impl.Activity;
import appLogic.employee.Employee;
import appLogic.project.ProjectRegistry;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import java.sql.SQLOutput;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
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
    private ActivityService activityService;
    private ProjectRegistry projectRegistry;

    public ShowActivitiesView(Scene scene) {
        this.scene = scene;
        this.app = App.getInstance();
        this.appContext = app.getAppContext();
        this.user = appContext.getLoggedInUser();
        this.username = this.user.getName();
        this.activitySet = this.user.getActivities();
        this.entries.addAll(activitySet);
        this.activityService = appContext.getActivityService();
        this.projectRegistry = appContext.getProjectRegistry();
    }

    public Parent getView() {
        BorderPane root = new BorderPane();
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(15));
        grid.setVgap(10);
        grid.setHgap(10);

        // Information in grid
        Label message = new Label("Showing activies for user: " + username);
        grid.add(message,2,1);

        Button backBtn = new Button("Back");
        grid.add(backBtn, 0, 1);

        Button fixedActivityButton = new Button("New fixed activity");
        grid.add(fixedActivityButton, 1,1);

        // Columns
        TableColumn<Activity, String> activityNameCol = new TableColumn<>("Activity description");
        activityNameCol.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getDescription()));

        TableColumn<Activity, String> startDateCol = new TableColumn<>("Start date");
        startDateCol.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getStartDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));

        TableColumn<Activity, String> endDateCol = new TableColumn<>("End date");
        endDateCol.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getEndDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));

        TableColumn<Activity, String> projectIDcol = new TableColumn<>("Project ID");
        projectIDcol.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getProjectId()));

        TableColumn<Activity, String> projectNamecol = new TableColumn<>("Project name");
        projectNamecol.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(projectRegistry.getProjectById(data.getValue().getProjectId()).getProjectName()));

        TableView<Activity> tableData = new TableView<>();
        tableData.setItems(entries);
        tableData.getColumns().addAll(activityNameCol, startDateCol, endDateCol, projectIDcol, projectNamecol);

        // Event handlers
        backBtn.setOnAction(e -> {
            MainView mainView = new MainView(scene);
            scene.setRoot(mainView.getView());
        });

        fixedActivityButton.setOnAction(e -> openNewActivityDialog());

        root.setTop(grid);
        root.setCenter(tableData);
        return root;
    }

    private void openNewActivityDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("New Fixed activity");
        dialog.setHeaderText("Create fixed activity");

        DatePicker startDate   = new DatePicker(LocalDate.now());
        DatePicker endDate     = new DatePicker(LocalDate.now());

        ChoiceBox<String> fixedActivityChoice = new ChoiceBox<>();
        // Stream, get enum to array of strings.
        fixedActivityChoice.getItems().addAll(Arrays.stream(FixedActivityType.values()).map(FixedActivityType::toString).toList());

        GridPane grid = new GridPane();
        grid.setHgap(10); grid.setVgap(10); grid.setPadding(new Insets(10));
        grid.add(new Label("Choose fixed activity"),0,1); grid.add(fixedActivityChoice, 1,1);
        grid.add(new Label("Start date:"),     0, 2); grid.add(startDate,    1, 2);
        grid.add(new Label("End date:"),       0, 3); grid.add(endDate,      1, 3);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.showAndWait().ifPresent(result -> {
            if (result != ButtonType.OK) return;

            // Create fixed activity
            Activity activity = activityService.createFixedActivity(new CreateFixedActivity(
                    startDate.getValue().atStartOfDay(), endDate.getValue().atStartOfDay(), FixedActivityType.valueOf(fixedActivityChoice.getValue())
            ));

            user.addActivity(activity);
            entries.add(activity);
        });
    }
}
