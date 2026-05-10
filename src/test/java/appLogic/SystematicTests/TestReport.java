package appLogic.SystematicTests;

import appLogic.App;
import appLogic.AppContext;
import appLogic.employee.Employee;
import appLogic.project.Project;
import appLogic.report.Report;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class TestReport {

        private final App app = App.getInstance();

        @AfterEach
        void reset() {
        app.getAppContext().reset();
        App.resetInstanceForTests();
        }

        @Test
        void generateReport_unknownProject_throwsIllegalArgument() {
                assertThrows(IllegalArgumentException.class, // Try-catch
                        () -> app.getReport("NONEXISTENT"));
                App.resetInstanceForTests();
        }

        @Test
        void generateReport_noActivities_returnsZeroHoursAndFullRemainder() {
                App app = App.getInstance();
                Project p = app.getProjectRegistry().getAllProjects().get(0);
                p.setExpectedHours(10);

                Report report = app.getReport(p.getProjectID());

                assertEquals(0.0, report.hoursUsed());
                assertTrue(report.activitySummaries().isEmpty());
                assertEquals(10.0, report.expectedRemainingHours());
                App.resetInstanceForTests();
        }


    @Test
    void generateReport_withActivities_hoursUnderBudget_returnsCorrectSummary() {
        App app = App.getInstance();
        AppContext appContext = app.getAppContext();
        appContext.login("laha");
        Project p = app.getProjectRegistry().getProjectByName("KBHShop");
        p.setExpectedHours(10);

        Employee laha = app.getLoggedInUser();
        var activity = app.getActivityService().createWorkActivity(
                new appLogic.activity.command.CreateWorkActivity(
                        p.getProjectID(), "TestTask", "desc", "summary",
                        LocalDateTime.now(), LocalDateTime.now(), 5
                )
        );
        app.getActivityService().registerWork(
                activity.getId(), laha, LocalDateTime.now(), LocalDateTime.now(), 3.0
        );

        Report report = app.getReport(p.getProjectID());

        assertEquals(3.0, report.hoursUsed());
        assertEquals(1, report.activitySummaries().size());
        assertEquals(7.0, report.expectedRemainingHours());
        App.resetInstanceForTests();
    }

    @Test
    void generateReport_hoursOverBudget_remainingHoursClampsToZero() {
        App app = App.getInstance();
        AppContext appContext = app.getAppContext();
        appContext.login("laha");
        Project p = app.getProjectRegistry().getProjectByName("KBHShop");
        p.setExpectedHours(10);

        Employee laha = app.getLoggedInUser();
        var activity = app.getActivityService().createWorkActivity(
                new appLogic.activity.command.CreateWorkActivity(
                        p.getProjectID(), "BigTask", "desc", "summary",
                        LocalDateTime.now(), LocalDateTime.now(), 5
                )
        );
        app.getActivityService().registerWork(
                activity.getId(), laha, LocalDateTime.now(), LocalDateTime.now(), 15.0
        );

        Report report = app.getReport(p.getProjectID());

        assertEquals(15.0, report.hoursUsed());
        assertEquals(1, report.activitySummaries().size());
        assertEquals(0.0, report.expectedRemainingHours());
        App.resetInstanceForTests();
    }
}
