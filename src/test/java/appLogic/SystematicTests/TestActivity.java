package appLogic.SystematicTests;

import appLogic.project.ProjectActivity;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestActivity {

    // isOverlapWeek has 8 execution paths based on year/week comparisons.
    // Object attributes: startWeek, startYear, endWeek, endYear
    // Arguments: week, year

    // Path 1: year < startYear OR year > endYear → false
    // Input: activity(startWeek=10, startYear=2025, endWeek=20, endYear=2026), week=15, year=2024
    @Test
    void isOverlapWeek_yearOutOfRange_returnsFalse() {
        ProjectActivity a = makeActivity(10, 2025, 20, 2026);
        assertFalse(a.isOverlapWeek(15, 2024));
        assertFalse(a.isOverlapWeek(15, 2027));
    }

    // Path 2: startYear == endYear && week >= startWeek && week <= endWeek → true
    // Input: activity(startWeek=10, startYear=2026, endWeek=20, endYear=2026), week=15, year=2026
    @Test
    void isOverlapWeek_sameYear_weekInRange_returnsTrue() {
        ProjectActivity a = makeActivity(10, 2026, 20, 2026);
        assertTrue(a.isOverlapWeek(15, 2026));
    }

    // Path 3: startYear == endYear && (week < startWeek || week > endWeek) → false
    // Input: activity(startWeek=10, startYear=2026, endWeek=20, endYear=2026), week=5, year=2026
    @Test
    void isOverlapWeek_sameYear_weekOutOfRange_returnsFalse() {
        ProjectActivity a = makeActivity(10, 2026, 20, 2026);
        assertFalse(a.isOverlapWeek(5, 2026));
        assertFalse(a.isOverlapWeek(25, 2026));
    }

    // Path 4: startYear != endYear && year == startYear && week >= startWeek → true
    // Input: activity(startWeek=10, startYear=2025, endWeek=20, endYear=2026), week=15, year=2025
    @Test
    void isOverlapWeek_startYear_weekAfterStart_returnsTrue() {
        ProjectActivity a = makeActivity(10, 2025, 20, 2026);
        assertTrue(a.isOverlapWeek(15, 2025));
    }

    // Path 5: startYear != endYear && year == startYear && week < startWeek → false
    // Input: activity(startWeek=10, startYear=2025, endWeek=20, endYear=2026), week=5, year=2025
    @Test
    void isOverlapWeek_startYear_weekBeforeStart_returnsFalse() {
        ProjectActivity a = makeActivity(10, 2025, 20, 2026);
        assertFalse(a.isOverlapWeek(5, 2025));
    }

    // Path 6: startYear != endYear && year == endYear && week <= endWeek → true
    // Input: activity(startWeek=10, startYear=2025, endWeek=20, endYear=2026), week=15, year=2026
    @Test
    void isOverlapWeek_endYear_weekBeforeEnd_returnsTrue() {
        ProjectActivity a = makeActivity(10, 2025, 20, 2026);
        assertTrue(a.isOverlapWeek(15, 2026));
    }

    // Path 7: startYear != endYear && year == endYear && week > endWeek → false
    // Input: activity(startWeek=10, startYear=2025, endWeek=20, endYear=2026), week=25, year=2026
    @Test
    void isOverlapWeek_endYear_weekAfterEnd_returnsFalse() {
        ProjectActivity a = makeActivity(10, 2025, 20, 2026);
        assertFalse(a.isOverlapWeek(25, 2026));
    }

    // Path 8: startYear != endYear && year != startYear && year != endYear → true (middle year)
    // Input: activity(startWeek=10, startYear=2024, endWeek=20, endYear=2026), week=30, year=2025
    @Test
    void isOverlapWeek_middleYear_anyWeek_returnsTrue() {
        ProjectActivity a = makeActivity(10, 2024, 20, 2026);
        assertTrue(a.isOverlapWeek(30, 2025));
    }

    // NOTE: makeActivity uses reflection because ProjectActivity has no
    // constructor or setters for startWeek/startYear/endWeek/endYear.
    // These fields should ideally be set via constructor to avoid this.
    private ProjectActivity makeActivity(int startWeek, int startYear, int endWeek, int endYear) {
        ProjectActivity a = new ProjectActivity("test");
        try {
            setField(a, "startWeek", startWeek);
            setField(a, "startYear", startYear);
            setField(a, "endWeek", endWeek);
            setField(a, "endYear", endYear);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return a;
    }

    private void setField(Object obj, String name, int value) throws Exception {
        var field = obj.getClass().getDeclaredField(name);
        field.setAccessible(true);
        field.set(obj, value);
    }
}