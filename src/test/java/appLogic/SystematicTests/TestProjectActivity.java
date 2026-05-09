package appLogic.SystematicTests;

import appLogic.project.ProjectActivity;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestProjectActivity {

    // isOverlapWeek has 8 execution paths based on year/week comparisons

    @Test
    void isOverlapWeek_yearOutOfRange_returnsFalse() {
        ProjectActivity a = makeActivity(10, 2025, 20, 2026);
        assertFalse(a.isOverlapWeek(15, 2024));
        assertFalse(a.isOverlapWeek(15, 2027));
    }

    @Test
    void isOverlapWeek_sameYear_weekInRange_returnsTrue() {
        ProjectActivity a = makeActivity(10, 2026, 20, 2026);
        assertTrue(a.isOverlapWeek(15, 2026));
    }

    @Test
    void isOverlapWeek_sameYear_weekOutOfRange_returnsFalse() {
        ProjectActivity a = makeActivity(10, 2026, 20, 2026);
        assertFalse(a.isOverlapWeek(5, 2026));
        assertFalse(a.isOverlapWeek(25, 2026));
    }

    @Test
    void isOverlapWeek_startYear_weekAfterStart_returnsTrue() {
        ProjectActivity a = makeActivity(10, 2025, 20, 2026);
        assertTrue(a.isOverlapWeek(15, 2025));
    }

    @Test
    void isOverlapWeek_startYear_weekBeforeStart_returnsFalse() {
        ProjectActivity a = makeActivity(10, 2025, 20, 2026);
        assertFalse(a.isOverlapWeek(5, 2025));
    }

    @Test
    void isOverlapWeek_endYear_weekBeforeEnd_returnsTrue() {
        ProjectActivity a = makeActivity(10, 2025, 20, 2026);
        assertTrue(a.isOverlapWeek(15, 2026));
    }


    @Test
    void isOverlapWeek_endYear_weekAfterEnd_returnsFalse() {
        ProjectActivity a = makeActivity(10, 2025, 20, 2026);
        assertFalse(a.isOverlapWeek(25, 2026));
    }

    @Test
    void isOverlapWeek_middleYear_anyWeek_returnsTrue() {
        ProjectActivity a = makeActivity(10, 2024, 20, 2026);
        assertTrue(a.isOverlapWeek(30, 2025));
    }

    private ProjectActivity makeActivity(int startWeek, int startYear, int endWeek, int endYear) {
        return new ProjectActivity("test", "", "", startWeek, endWeek, startYear, endYear, null);
    }
}
