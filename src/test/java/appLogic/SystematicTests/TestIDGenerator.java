package appLogic.SystematicTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import appLogic.project.ProjectIdGenerator;

public class TestIDGenerator {
    @Test
    void testProjectIdSequenceAndPadding() {
        ProjectIdGenerator gen = new ProjectIdGenerator();

        // First call should return "26001" if padding is correct
        assertEquals("26001", gen.generateId());
    
        // Call 5 more times to test sequence incrementing and padding
        for(int i = 0; i < 5; i++) {
            gen.generateId();
        }
    
        // testing the 0 padding for 3 digits
        assertEquals("26007", gen.generateId());
    }
}
