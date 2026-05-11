// s245072 Lea
package appLogic.SystematicTests;

import appLogic.App;
import appLogic.employee.Employee;
import appLogic.employee.InMemoryEmployeeRepository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class TestEmployRepo {

    private InMemoryEmployeeRepository repo;

    @BeforeEach
    void setUp() {
        repo = new InMemoryEmployeeRepository();
    }

    @AfterEach
    void reset() {
        App.resetInstanceForTests();
    }

    // findAll

    @Test
    void findAll_returnsImmutableCopyOfAllEmployees() {
        InMemoryEmployeeRepository appRepo = (InMemoryEmployeeRepository) App.getInstance().getEmployeeRepository();

        Set<Employee> all = appRepo.findAll();

        assertFalse(all.isEmpty());
    }

    // loadFromFile

    @Test
    void loadFromFile_nonExistentFile_doesNotThrowAndRepoRemainsEmpty() {
        assertDoesNotThrow(() -> repo.loadFromFile("nonexistent-file.txt"));
        assertTrue(repo.getEmployees().isEmpty());
    }

    //loadFromFile: malformed line

    @Test
    void loadFromFile_malformedLine_isSkipped() {
        repo.loadFromFile("employees-malformed.txt");

        assertNotEquals(3, repo.getEmployees().size(), "Malformed linje må ikke tilføjes");
    }
}