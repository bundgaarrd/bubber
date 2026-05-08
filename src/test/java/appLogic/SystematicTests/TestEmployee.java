package appLogic.SystematicTests;

import appLogic.employee.Employee;
import appLogic.project.Project;
import org.junit.jupiter.api.Test;
import appLogic.activity.impl.Activity;
import appLogic.App;
import appLogic.activity.impl.WorkActivity;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;

public class TestEmployee {
    @AfterEach
    void reset() {
        App.resetInstanceForTests();
    }

    @Test
    void getAvailableEmployees_returnsOnlyAvailable() {
        App app = App.getInstance();
        assertFalse(app.getAvailableEmployees().isEmpty());
        app.getAvailableEmployees().forEach(e -> assertTrue(e.isAvailable()));
    }

    @Test
    void getActiveActivityCount_noActivities_returnsZero() {
        Employee emp = new Employee("test", "Test User", true);
        assertEquals(0, emp.getActiveActivityCount(1, 2026));
    }

    @Test
    void getActiveActivityCount_activityMatchingWeekAndYear_returnsOne() {
        Employee emp = new Employee("test", "Test User", true);
        LocalDate date = LocalDate.of(2026, 1, 5); // week 2, 2026
        Activity activity = new WorkActivity("task", "", "", 0, 0, 0, 0, 10);
        emp.addActivity(activity);

        int week = date.get(java.time.temporal.WeekFields.of(java.util.Locale.getDefault()).weekOfWeekBasedYear());
        assertEquals(1, emp.getActiveActivityCount(week, 2026));
    }

    @Test
    void getActiveActivityCount_activityInDifferentYear_returnsZero() {
        Employee emp = new Employee("test", "Test User", true);
        LocalDate date = LocalDate.of(2025, 1, 5);
        Activity activity = new WorkActivity("task", "", "", 0, 0, 0, 0, 10);
        emp.addActivity(activity);

        assertEquals(0, emp.getActiveActivityCount(1, 2026));
    }

    @Test
    void addProjectAsLeader_andRemove_updatesLeaderProjects() {
        Employee emp = new Employee("test", "Test User", true);
        Project p = new Project("99001", "TestProject");
        emp.addProjectAsLeader(p);
        assertTrue(emp.getLeaderProjects().contains(p));
        emp.removeProjectAsLeader(p);
        assertFalse(emp.getLeaderProjects().contains(p));
    }

    @Test
    void setIsAvailable_changesAvailability() {
        Employee emp = new Employee("test", "Test User", true);
        emp.setIsAvailable(false);
        assertFalse(emp.isAvailable());
    }
}
