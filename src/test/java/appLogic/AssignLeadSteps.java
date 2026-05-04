package appLogic;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;

public class AssignLeadSteps {

    private Employee candidate;

    @Given("there is a project named {string} without a project leader")
    public void thereIsAProjectWithoutAProjectLeader(String projectName) {
        Project project = TestApp.getInstance().getApp().getProjectByName(projectName);
        if (project == null) {
            project = TestApp.getInstance().getApp().createProject(projectName);
        }
        Assertions.assertNull(project.getProjectLeader(),
                "Expected project to have no leader, but it already has one");
        TestApp.getInstance().setProject(project);
    }

    @When("an employee {string} is chosen to be the project leader for a project")
    public void anEmployeeIsChosenToBeProjectLeader(String initials) {
        candidate = TestApp.getInstance().getEmployeeByInitials(initials);
        Assertions.assertNotNull(candidate, "Employee " + initials + " not found");
    }

    @And("the selected employee confirms the role of project leader")
    public void theSelectedEmployeeConfirms() {
        Project project = TestApp.getInstance().getProject();
        project.assignProjectLeader(candidate);
    }

    @And("the selected employee does not confirm the role of project leader")
    public void theSelectedEmployeeDoesNotConfirm() {
        // Do nothing — no assignment made
    }

    @Then("the selected employee is added as the project leader for the project")
    public void leaderIsAdded() {
        Project project = TestApp.getInstance().getProject();
        Assertions.assertEquals(candidate, project.getProjectLeader(),
                "Expected candidate to be project leader, but they are not");
    }

    @Then("the selected employee is not added as the project leader for the project")
    public void leaderIsNotAdded() {
        Project project = TestApp.getInstance().getProject();
        Assertions.assertNull(project.getProjectLeader(),
                "Expected no project leader, but one was assigned");
    }
}