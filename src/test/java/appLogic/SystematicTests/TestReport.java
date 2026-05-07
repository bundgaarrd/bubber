package appLogic.SystematicTests;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import appLogic.App;
import appLogic.Report;
import appLogic.project.Project;

public class TestReport {
    @Test
    void generateReport_unknownProject_throwsIllegalArgument() {
        App app = App.getInstance();
        assertThrows(IllegalArgumentException.class, () -> app.generateReport("NONEXISTENT"));
    }

    @Test
    void generateReport_validProject_returnsReport() {
        App app = App.getInstance();
        app.login("laha");
        Project p = app.getProjectRegistry().getProjectByName("KBHShop");
        Report report = app.generateReport(p.getProjectID());
        assertNotNull(report);
        assertTrue(report.getHoursUsed() >= 0);
    }
}
