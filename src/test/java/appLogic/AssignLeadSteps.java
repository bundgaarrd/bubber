package appLogic;

import appLogic.employee.Employee;
import appLogic.project.Project;
import io.cucumber.java.en.*;
import org.junit.jupiter.api.Assertions;

public class AssignLeadSteps {

    private Employee candidate;
    private Exception lastException;

    @Given("there is a project named {string} without a project leader")
    public void thereIsAProjectWithoutAProjectLeader(String projectName) {
        Project project = TestApp.getInstance().getProjectRegistry().getProjectByName(projectName);
        if (project == null) {
            project = TestApp.getInstance().getProjectRegistry().createProject(projectName);
        }
        Assertions.assertNull(project.getProjectLeader(),
                "Expected project to have no leader, but it already has one");
        TestApp.getInstance().setProject(project);
    }

    @Given("there is a project named {string} with a project leader {string}")
    public void thereIsAProjectWithAProjectLeader(String projectName, String initials) {
        Project project = TestApp.getInstance().getProjectRegistry().getProjectByName(projectName);
        if (project == null) {
            project = TestApp.getInstance().getProjectRegistry().createProject(projectName);
        }

        Employee leader = TestApp.getInstance().getEmployeeByInitials(initials);
        Assertions.assertNotNull(leader, "Employee not found");

        project.assignProjectLeader(leader);
        TestApp.getInstance().setProject(project);
    }

    @When("an employee {string} is assigned to be the project leader for {string}")
    public void assignProjectLeader(String initials, String projectName) {
        candidate = TestApp.getInstance().getEmployeeByInitials(initials);
        Assertions.assertNotNull(candidate, "Employee " + initials + " not found");

        Project project = TestApp.getInstance().getProjectRegistry().getProjectByName(projectName);
        Assertions.assertNotNull(project, "Project not found");

        try {
            project.assignProjectLeader(candidate);
            lastException = null;
        } catch (Exception e) {
            lastException = e;
        }

        TestApp.getInstance().setProject(project);
    }

    @Then("{string} is added as the project leader for {string}")
    public void isAdded(String initials, String projectName) {
        Project project = TestApp.getInstance().getProject();
        Assertions.assertEquals(initials, project.getProjectLeader().getInitials());
    }

    @Then("{string} is not added as the project leader for {string}")
    public void isNotAdded(String initials, String projectName) {
        Project project = TestApp.getInstance().getProject();

        if (project.getProjectLeader() != null) {
            Assertions.assertNotEquals(initials, project.getProjectLeader().getInitials());
        }
    }

    @Then("{string} remains the project leader for {string}")
    public void remainsLeader(String initials, String projectName) {
        Project project = TestApp.getInstance().getProject();
        Assertions.assertEquals(initials, project.getProjectLeader().getInitials());
    }

    @Then("An error message is shown indicating that {string} already has the project leader {string}")
    public void errorMessage(String projectName, String initials) {
        Assertions.assertNotNull(lastException, "Expected an error but none occurred");

        // Optional: gør den robust i stedet for exact string match
        Assertions.assertTrue(lastException.getMessage().toLowerCase().contains("leader"),
                "Expected error message about existing project leader");
    }
}