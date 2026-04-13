package appLogic;

import java.util.Set;

public interface EmployeeRepository {
    Employee findByInitials(String initials);
    Set<Employee> findAll();
    void save(Employee empl);
    void loadFromFile(String path);
}
