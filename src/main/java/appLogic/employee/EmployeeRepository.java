//s244813
package appLogic.employee;

import java.util.Map;
import java.util.Set;

public interface EmployeeRepository {
    Employee findByInitials(String initials);
    Employee findByName(String name);
    Set<Employee> findAll();
    void save(Employee empl);
    void loadFromFile(String path);
    Map<String, Employee> getEmployees();
}