// Lavet af Lea - s245072
package appLogic.project;
import java.time.Year;

public class ProjectIdGenerator {
    private int currentSequence;

    public ProjectIdGenerator(){
        this.currentSequence = 1;
    }

    public String generateId() {
        int shortYear = Year.now().getValue() % 100; // Last two digits of year
        String genID = String.format("%02d%03d", shortYear, currentSequence); // Format as YY + 3 digits
        // %03d ensures 1 becomes 001, 10 becomes 010, etc.
        currentSequence++;
        return genID;
    }
}