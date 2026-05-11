package ui; // Lavet af Andreas (s244970) og Valdemar (s246575)

import appLogic.App;
import appLogic.Summary;
import appLogic.employee.Employee;
import appLogic.project.Project;
import appLogic.report.Report;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class ReportView {
    private Scene scene;
    private Project project;
    private App app;

    public ReportView(Scene scene, Project project) {
        this.scene = scene;
        this.project = project;
        this.app = App.getInstance();
    }

    public Parent getView() {
        Employee loggedInUser = app.getLoggedInUser();
        Employee projectLeader = project.getProjectLeader();

        // Check if user is project leader
        if (projectLeader == null || !loggedInUser.equals(projectLeader)) {
            return createUnauthorizedView();
        }

        BorderPane root = new BorderPane();
        Report report = app.getReport(project.getProjectID());

        // Header section with hours metrics
        VBox headerSection = createHeaderSection(report);
        root.setTop(headerSection);

        // Table section with activity summaries
        VBox tableSection = createTableSection(report);
        root.setCenter(tableSection);

        // Navigation button
        VBox bottomSection = createBottomSection();
        root.setBottom(bottomSection);

        return root;
    }

    private Parent createUnauthorizedView() {
        VBox root = new VBox(20);
        root.setPadding(new Insets(40));
        root.setAlignment(Pos.CENTER);

        Label errorTitle = new Label("Access Denied");
        errorTitle.setStyle("-fx-font-size: 18; -fx-font-weight: bold; -fx-text-fill: #c94444;");

        Label errorMessage = new Label("Only the project leader can generate reports.");
        errorMessage.setStyle("-fx-font-size: 14; -fx-text-fill: #666666;");

        Button backBtn = new Button("Back");
        backBtn.setOnAction(e -> scene.setRoot(new RegisterActivityTimeView(scene, project).getView()));

        root.getChildren().addAll(errorTitle, errorMessage, backBtn);
        return root;
    }

    private VBox createHeaderSection(Report report) {
        VBox headerBox = new VBox(10);
        headerBox.setPadding(new Insets(15));
        headerBox.setStyle("-fx-border-color: #cccccc; -fx-border-width: 0 0 1 0;");

        Label projectLabel = new Label("Project: " + project.getProjectName());
        projectLabel.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");

        GridPane metricsGrid = new GridPane();
        metricsGrid.setHgap(30);
        metricsGrid.setVgap(10);
        metricsGrid.setPadding(new Insets(10, 0, 0, 0));

        Label hoursUsedLabel = new Label("Hours Used:");
        hoursUsedLabel.setStyle("-fx-font-weight: bold;");
        Label hoursUsedValue = new Label(String.format("%.1f hours", report.hoursUsed()));

        Label remainingLabel = new Label("Expected Remaining Hours:");
        remainingLabel.setStyle("-fx-font-weight: bold;");
        Label remainingValue = new Label(String.format("%.1f hours", report.expectedRemainingHours()));

        metricsGrid.add(hoursUsedLabel, 0, 0);
        metricsGrid.add(hoursUsedValue, 1, 0);
        metricsGrid.add(remainingLabel, 0, 1);
        metricsGrid.add(remainingValue, 1, 1);

        headerBox.getChildren().addAll(projectLabel, metricsGrid);
        return headerBox;
    }

    private VBox createTableSection(Report report) {
        VBox tableBox = new VBox(10);
        tableBox.setPadding(new Insets(15));

        Label activitiesLabel = new Label("Activity Summaries");
        activitiesLabel.setStyle("-fx-font-size: 14; -fx-font-weight: bold;");

        ObservableList<Summary> summaryData = FXCollections.observableArrayList();
        if (report.activitySummaries() != null) {
            summaryData.addAll(report.activitySummaries());
        }

        TableView<Summary> tableView = new TableView<>();
        tableView.setItems(summaryData);

        TableColumn<Summary, String> summaryCol = new TableColumn<>("Activity Summary");
        summaryCol.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().activitySummary()));

        TableColumn<Summary, String> hoursCol = new TableColumn<>("Hours Used");
        hoursCol.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(String.format("%.1f", data.getValue().hoursUsed())));

        TableColumn<Summary, String> expectedHoursCol = new TableColumn<>("Expected hours");
        expectedHoursCol.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(String.format("%.1f", data.getValue().expectedHours())));

        tableView.getColumns().addAll(summaryCol, hoursCol, expectedHoursCol);

        if (report.activitySummaries().isEmpty()) {
            Label noActivitiesLabel = new Label("No activities recorded for this project.");
            noActivitiesLabel.setStyle("-fx-font-size: 12; -fx-text-fill: #666666;");
            tableBox.getChildren().addAll(activitiesLabel, noActivitiesLabel);
        } else {
            tableBox.getChildren().addAll(activitiesLabel, tableView);
        }

        return tableBox;
    }

    private VBox createBottomSection() {
        VBox bottomBox = new VBox();
        bottomBox.setPadding(new Insets(10));
        bottomBox.setAlignment(Pos.CENTER_LEFT);

        Button backBtn = new Button("Back");
        backBtn.setOnAction(e -> scene.setRoot(new RegisterActivityTimeView(scene, project).getView()));

        bottomBox.getChildren().add(backBtn);
        return bottomBox;
    }
}
