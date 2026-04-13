package appLogic;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class InMemoryEmployeeRepository implements EmployeeRepository {

    private Map<String, Employee> employees = new HashMap<>();

    @Override
    public Employee findByInitials(String initials) {
        return employees.get(initials);
    }

    @Override
    public Set<Employee> findAll() {
        return Set.copyOf(employees.values());
    }

    @Override
    public void save(Employee empl) {
        employees.put(empl.getInitials(), empl);
    }

    @Override
    public void loadFromFile(String path) {
        // der skal lige laves readfile logik her men tænker det skal se sådan her ud. 
        
        System.out.println("Loading employees from: " + path);
    }
}