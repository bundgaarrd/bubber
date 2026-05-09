package appLogic.SystematicTests;

import appLogic.project.Project;
import appLogic.project.ProjectRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestProjectRegistry {

    // Attributes: projects (Set), idGenerator
    // Each test constructs a fresh registry to control object state precisely.

    private ProjectRegistry registry;

    @BeforeEach
    void setup() {
        registry = new ProjectRegistry();
    }

    // --- register ---

    // Path 1: lookup returns null (no duplicate) → project added
    // Input: projects empty, project name "Alpha"
    // Output: projects contains "Alpha"
    @Test
    void register_newProject_isAddedToRegistry() {
        Project p = new Project("99001", "Alpha");
        registry.register(p);
        assertNotNull(registry.getProjectByName("Alpha"));
    }

    // Path 2: lookup returns non-null (duplicate name) → throws IllegalStateException
    // Input: projects contains "Alpha", register another "Alpha"
    // Output: IllegalStateException thrown, projects unchanged
    @Test
    void register_duplicateName_throwsIllegalState() {
        registry.register(new Project("99001", "Alpha"));
        assertThrows(IllegalStateException.class,
                () -> registry.register(new Project("99002", "Alpha")));
    }

    // --- lookup ---

    // Path 1: project with matching name found → returns project
    // Input: projects contains "Alpha", lookup "Alpha"
    // Output: returns the Alpha project
    @Test
    void lookup_existingName_returnsProject() {
        Project p = new Project("99001", "Alpha");
        registry.register(p);
        assertEquals(p, registry.getProjectByName("Alpha"));
    }

    // Path 2: no project with matching name → returns null
    // Input: projects empty, lookup "Alpha"
    // Output: null
    @Test
    void lookup_unknownName_returnsNull() {
        assertNull(registry.getProjectByName("Alpha"));
    }

    // --- deleteProjectByName ---

    // Path 1: project with name exists → removed from projects
    // Input: projects contains "Alpha", delete "Alpha"
    // Output: projects no longer contains "Alpha"
    @Test
    void deleteProjectByName_existingProject_removesIt() {
        registry.register(new Project("99001", "Alpha"));
        registry.deleteProjectByName("Alpha");
        assertNull(registry.getProjectByName("Alpha"));
    }

    // Path 2: no project with name → throws IllegalStateException
    // Input: projects empty, delete "Alpha"
    // Output: IllegalStateException thrown
    @Test
    void deleteProjectByName_unknownName_throwsIllegalState() {
        assertThrows(IllegalStateException.class,
                () -> registry.deleteProjectByName("Alpha"));
    }

    // --- deleteProjectById ---

    // 1 path: removeIf handles missing id silently — no branching
    // Input: projects contains id "99001", delete "99001"
    // Output: project no longer retrievable by id
    @Test
    void deleteProjectById_existingId_removesProject() {
        registry.register(new Project("99001", "Alpha"));
        registry.deleteProjectById("99001");
        assertNull(registry.getProjectById("99001"));
    }

    // --- getProjectById ---

    // Path 1: project with matching id found → returns project
    // Input: projects contains id "99001"
    // Output: returns the project
    @Test
    void getProjectById_existingId_returnsProject() {
        Project p = new Project("99001", "Alpha");
        registry.register(p);
        assertEquals(p, registry.getProjectById("99001"));
    }

    // Path 2: no project with matching id → returns null
    // Input: projects empty, query "99001"
    // Output: null
    @Test
    void getProjectById_unknownId_returnsNull() {
        assertNull(registry.getProjectById("99001"));
    }

    // --- createProject ---

    // 1 path: generates id via idGenerator, creates project, registers it
    // Input: projects empty, name "Beta"
    // Output: projects contains a project named "Beta" with a non-null generated id
    @Test
    void createProject_validName_returnsProjectWithGeneratedId() {
        Project p = registry.createProject("Beta");
        assertNotNull(p.getProjectID());
        assertEquals("Beta", p.getProjectName());
        assertEquals(p, registry.getProjectByName("Beta"));
    }
}