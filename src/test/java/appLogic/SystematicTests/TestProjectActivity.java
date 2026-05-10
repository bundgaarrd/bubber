package appLogic.SystematicTests;

import appLogic.project.ProjectActivity;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.DayOfWeek;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.Locale;

public class TestProjectActivity {

    // isOverlapWeek has 8 execution paths based on year/week comparisons

    @Test
    void isOverlapWeek_yearOutOfRange_returnsFalse() {
        ProjectActivity a = makeActivity(10, 20, 2025, 2026);
        assertFalse(a.isOverlapWeek(15, 2024));
        assertFalse(a.isOverlapWeek(15, 2027));
    }

    @Test
    void isOverlapWeek_sameYear_weekInRange_returnsTrue() {
        ProjectActivity a = makeActivity(10, 20, 2026, 2026);
        assertTrue(a.isOverlapWeek(15, 2026));
    }

    @Test
    void isOverlapWeek_sameYear_weekOutOfRange_returnsFalse() {
        ProjectActivity a = makeActivity(10, 20, 2026, 2026);
        assertFalse(a.isOverlapWeek(5, 2026));
        assertFalse(a.isOverlapWeek(25, 2026));
    }

    @Test
    void isOverlapWeek_startYear_weekAfterStart_returnsTrue() {
        ProjectActivity a = makeActivity(10, 20, 2025, 2026);
        assertTrue(a.isOverlapWeek(15, 2025));
    }

    @Test
    void isOverlapWeek_startYear_weekBeforeStart_returnsFalse() {
        ProjectActivity a = makeActivity(10, 20, 2025, 2026);
        assertFalse(a.isOverlapWeek(5, 2025));
    }

    @Test
    void isOverlapWeek_endYear_weekBeforeEnd_returnsTrue() {
        ProjectActivity a = makeActivity(10, 20, 2025, 2026);
        assertTrue(a.isOverlapWeek(15, 2026));
    }


    @Test
    void isOverlapWeek_endYear_weekAfterEnd_returnsFalse() {
        ProjectActivity a = makeActivity(10, 20, 2025, 2026);
        assertFalse(a.isOverlapWeek(25, 2026));
    }

    @Test
    void isOverlapWeek_middleYear_anyWeek_returnsTrue() {
        ProjectActivity a = makeActivity(10, 20, 2024, 2026);
        assertTrue(a.isOverlapWeek(30, 2025));
    }

    private ProjectActivity makeActivity(int startWeek, int endWeek, int startYear, int endYear) {
        LocalDate startLocal = weekStartDate(startYear, startWeek);
        LocalDate endLocal = weekStartDate(endYear, endWeek).plusDays(6);
        LocalDateTime startDateTime = startLocal.atStartOfDay();
        LocalDateTime endDateTime = endLocal.atTime(23, 59, 59);
        return new ProjectActivity("test", "", "", startDateTime, endDateTime, null);
    }

    private static LocalDate weekStartDate(int year, int week) {
        WeekFields wf = WeekFields.of(Locale.getDefault());
        LocalDate ref = LocalDate.of(year, 1, 4);
        return ref.with(wf.weekOfWeekBasedYear(), week).with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
    }
}
