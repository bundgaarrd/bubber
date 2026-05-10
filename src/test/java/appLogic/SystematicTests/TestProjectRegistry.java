package appLogic.SystematicTests;

import appLogic.project.Project;
import appLogic.project.ProjectRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestProjectRegistry {

    // Each test constructs fresh registry

    private ProjectRegistry registry;

    @BeforeEach
    void setup() {
        registry = new ProjectRegistry();
    }

    // register project


    @Test
    void register_newProject_isAddedToRegistry() {
        Project p = new Project("99001", "Alpha");
        registry.register(p);
        assertNotNull(registry.getProjectByName("Alpha"));
    }

    @Test
    void register_duplicateName_throwsIllegalState() {
        registry.register(new Project("99001", "Alpha"));
        assertThrows(IllegalStateException.class,
                () -> registry.register(new Project("99002", "Alpha")));
    }

    // lookup (med navn)

    @Test
    void lookup_existingName_returnsProject() {
        Project p = new Project("99001", "Alpha");
        registry.register(p);
        assertEquals(p, registry.getProjectByName("Alpha"));
    }

    @Test
    void lookup_unknownName_returnsNull() {
        assertNull(registry.getProjectByName("Alpha"));
    }

    // deleteProjectByName
    @Test
    void deleteProjectByName_existingProject_removesIt() {
        registry.register(new Project("99001", "Alpha"));
        registry.deleteProjectByName("Alpha");
        assertNull(registry.getProjectByName("Alpha"));
    }

    @Test
    void deleteProjectByName_unknownName_throwsIllegalState() {
        assertThrows(IllegalStateException.class,
                () -> registry.deleteProjectByName("Alpha"));
    }

    // deleteProjectById
    @Test
    void deleteProjectById_existingId_removesProject() {
        registry.register(new Project("99001", "Alpha"));
        registry.deleteProjectById("99001");
        assertNull(registry.getProjectById("99001"));
    }

    // getProjectById

    @Test
    void getProjectById_existingId_returnsProject() {
        Project p = new Project("99001", "Alpha");
        registry.register(p);
        assertEquals(p, registry.getProjectById("99001"));
    }

    @Test
    void getProjectById_unknownId_returnsNull() {
        assertNull(registry.getProjectById("99001"));
    }

    // createProject
    @Test
    void createProject_validName_returnsProjectWithGeneratedId() {
        Project p = registry.createProject("Beta");
        assertNotNull(p.getProjectID());
        assertEquals("Beta", p.getProjectName());
        assertEquals(p, registry.getProjectByName("Beta"));
    }
}