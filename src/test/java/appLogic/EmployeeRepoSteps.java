// s245072 Lea
package appLogic;

import appLogic.employee.Employee;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeRepoSteps {

    private Employee foundEmployee;
    private Set<Employee> foundEmployees;

    @When("I look up an employee by name {string}")
    public void iLookUpAnEmployeeByName(String name) {
        foundEmployee = TestApp.getInstance()
                .getApp()
                .getEmployeeRepository()
                .findByName(name);
    }

    @Then("the employee is found with initials {string}")
    public void theEmployeeIsFoundWithInitials(String initials) {
        assertNotNull(foundEmployee, "Expected to find an employee, but got null");
        assertEquals(initials, foundEmployee.getInitials());
    }

    @Then("no employee is found")
    public void noEmployeeIsFound() {
        assertNull(foundEmployee, "Expected no employee to be found, but got: " + foundEmployee);
    }

    @When("I look up available employees")
    public void iLookUpAvailableEmployees() {
        foundEmployees = TestApp.getInstance()
                .getApp()
                .getEmployeeRepository()
                .getAllAvailableEmployees();
    }

    @Then("available employees are listed")
    public void availableEmployeesAreListed() {
        assertNotNull(foundEmployees);
        assertFalse(foundEmployees.isEmpty(), "Expected at least one available employee");
        for (Employee emp : foundEmployees) {
            assertTrue(emp.isAvailable(),
                    "Employee " + emp.getInitials() + " is not available but was returned");
        }
    }

    @And("the employee {string} is found in the list of available employees")
    public void theEmployeeIsFoundInTheListOfAvailableEmployees(String initials) {
        assertNotNull(foundEmployees);
        boolean found = foundEmployees.stream()
                .anyMatch(emp -> emp.getInitials().equals(initials));
        assertTrue(found, "Expected employee " + initials + " to be in the available list, but they were not");
    }

    @And("the employee {string} is not found in the list of available employees")
    public void theEmployeeIsNotFoundInTheListOfAvailableEmployees(String initials) {
        assertNotNull(foundEmployees);
        boolean found = foundEmployees.stream()
                .anyMatch(emp -> emp.getInitials().equals(initials));
        assertFalse(found, "Expected employee " + initials + " to be excluded from the available list, but they were returned");
    }
}
