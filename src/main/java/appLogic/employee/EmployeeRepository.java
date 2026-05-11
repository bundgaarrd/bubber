package appLogic.employee; // Lavet af Artur (s244813) og Andreas (s244970)

import java.util.Map;
import java.util.Set;

public interface EmployeeRepository {
    Employee findByInitials(String initials);
    Employee findByName(String name);
    Set<Employee> findAll();
    void save(Employee empl);
    void loadFromFile(String path);
    Map<String, Employee> getEmployees();
    Set<Employee> getAllAvailableEmployees();
}