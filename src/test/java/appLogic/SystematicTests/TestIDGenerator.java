// Lavet af Lea - s245072
package appLogic.SystematicTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import appLogic.project.ProjectIdGenerator;
import java.time.Year;

public class TestIDGenerator {

    // Only one execution path in generateId() = no branching.
    // Tests cover the boundary cases where the %03d padding changes width.
    private final String YY = String.format("%02d", Year.now().getValue() % 100);


    @Test
    void generateId_sequenceOne_formatsWithLeadingZeros() {
        ProjectIdGenerator gen = new ProjectIdGenerator();
        assertEquals(YY + "001", gen.generateId());
    }

    @Test
    void generateId_sequenceNine_stillPadsTwoZeros() {
        ProjectIdGenerator gen = new ProjectIdGenerator();
        for (int i = 0; i < 8; i++) gen.generateId(); 
        assertEquals(YY + "009", gen.generateId());
    }


    @Test
    void generateId_sequenceTen_padsOneZero() {
        ProjectIdGenerator gen = new ProjectIdGenerator();
        for (int i = 0; i < 9; i++) gen.generateId(); 
        assertEquals(YY + "010", gen.generateId());
    }


    @Test
    void generateId_sequenceNineNineNine_noLeadingZero() {
        ProjectIdGenerator gen = new ProjectIdGenerator();
        for (int i = 0; i < 998; i++) gen.generateId();
        assertEquals(YY + "999", gen.generateId());
    }
}