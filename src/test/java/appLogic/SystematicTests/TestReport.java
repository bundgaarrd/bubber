package appLogic.SystematicTests;

import appLogic.App;
import appLogic.employee.Employee;
import appLogic.project.Project;
import appLogic.report.Report;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class TestReport {

    // Input: projectId not in projectRegistry
    // Path 1: project == null → throw
    @Test
    void generateReport_unknownProject_throwsIllegalArgument() {
        App app = App.getInstance();
        assertThrows(IllegalArgumentException.class, // Try-catch 
                () -> app.getReport("NONEXISTENT"));
        App.resetInstanceForTests();
    }

    // Input: valid project, no activities, expectedHours = 10
    // Path 2: project found, loop body never executes
    // Output: hoursUsed=0, summaries empty, remainingHours = 10
    @Test
    void generateReport_noActivities_returnsZeroHoursAndFullRemainder() {
        App app = App.getInstance();
        Project p = app.getProjectRegistry().getAllProjects().get(0);
        p.setExpectedHours(10);

        Report report = app.getReport(p.getProjectID());

        assertEquals(0.0, report.getHoursUsed());
        assertTrue(report.getActivitySummaries().isEmpty());
        assertEquals(10, report.getExpectedRemainingHours());
        App.resetInstanceForTests();
    }

    // Input: valid project, 1 activity with 3 hours logged, expectedHours = 10
    // Path 3: project found, loop executes once, totalHoursUsed(3) < expectedHours(10)
    // Output: hoursUsed=3, summaries has 1 entry, remainingHours=7
    @Test
    void generateReport_withActivities_hoursUnderBudget_returnsCorrectSummary() {
        App app = App.getInstance();
        app.login("laha");
        Project p = app.getProjectRegistry().getProjectByName("KBHShop");
        p.setExpectedHours(10);

        Employee laha = app.getLoggedInUser();
        var activity = app.getActivityService().createProjectActivity(
                new appLogic.activity.command.CreateProjectActivity(
                        p.getProjectID(), "TestTask", "desc", "summary", LocalDate.now()
                )
        );
        app.getActivityService().registerWork(
                activity.getId(), laha, LocalDateTime.now(), 3.0
        );

        Report report = app.getReport(p.getProjectID());

        assertEquals(3.0, report.getHoursUsed());
        assertEquals(1, report.getActivitySummaries().size());
        assertEquals(7, report.getExpectedRemainingHours());
        App.resetInstanceForTests();
    }

    // Input: valid project, 1 activity with 15 hours logged, expectedHours = 10
    // Path: project found, loop executes, totalHoursUsed(15) >= expectedHours(10) → Math.max clamps to 0
    // Output: hoursUsed=15, summaries has 1 entry, remainingHours=0
    @Test
    void generateReport_hoursOverBudget_remainingHoursClampsToZero() {
        App app = App.getInstance();
        app.login("laha");
        Project p = app.getProjectRegistry().getProjectByName("KBHShop");
        p.setExpectedHours(10);

        Employee laha = app.getLoggedInUser();
        var activity = app.getActivityService().createProjectActivity(
                new appLogic.activity.command.CreateProjectActivity(
                        p.getProjectID(), "BigTask", "desc", "summary", LocalDate.now()
                )
        );
        app.getActivityService().registerWork(
                activity.getId(), laha, LocalDateTime.now(), 15.0
        );

        Report report = app.getReport(p.getProjectID());

        assertEquals(15.0, report.getHoursUsed());
        assertEquals(1, report.getActivitySummaries().size());
        assertEquals(0, report.getExpectedRemainingHours());
        App.resetInstanceForTests();
    }
}
