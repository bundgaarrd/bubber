package appLogic.SystematicTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import appLogic.project.ProjectIdGenerator;
import java.time.Year;

public class TestIDGenerator {

    // Only one execution path in generateId() = no branching.
    // Input is the object attribute: currentSequence.
    // Tests cover the boundary cases where the %03d padding changes width.
    private final String YY = String.format("%02d", Year.now().getValue() % 100);

    // Input: currentSequence = 1 (fresh generator)
    // Output: "YY001", currentSequence becomes 2
    @Test
    void generateId_sequenceOne_formatsWithLeadingZeros() {
        ProjectIdGenerator gen = new ProjectIdGenerator();
        assertEquals(YY + "001", gen.generateId());
    }

    // Input: currentSequence = 9 (last single-digit value)
    // Output: "YY009", currentSequence becomes 10
    @Test
    void generateId_sequenceNine_stillPadsTwoZeros() {
        ProjectIdGenerator gen = new ProjectIdGenerator();
        for (int i = 0; i < 8; i++) gen.generateId(); 
        assertEquals(YY + "009", gen.generateId());
    }

    // Input: currentSequence = 10 (two-digit value, padding should drop to 1 zero)
    // Output: "YY010", currentSequence becomes 11
    @Test
    void generateId_sequenceTen_padsOneZero() {
        ProjectIdGenerator gen = new ProjectIdGenerator();
        for (int i = 0; i < 9; i++) gen.generateId(); // advance to sequence 10
        assertEquals(YY + "010", gen.generateId());
    }

    // Input: currentSequence = 999 (last value that fits in 3 digits)
    // Output: "YY999", currentSequence becomes 1000
    @Test
    void generateId_sequenceNineNineNine_noLeadingZero() {
        ProjectIdGenerator gen = new ProjectIdGenerator();
        for (int i = 0; i < 998; i++) gen.generateId();
        assertEquals(YY + "999", gen.generateId());
    }
}