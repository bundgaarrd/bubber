package appLogic.employee;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class InMemoryEmployeeRepository implements EmployeeRepository {
    private static final System.Logger logger = System.getLogger(InMemoryEmployeeRepository.class.getName());

    private final Map<String, Employee> employees = new HashMap<>();

    @Override
    public Employee findByInitials(String initials) {
        return employees.get(initials);
    }

    @Override
    public Employee findByName(String name) {
        for (Employee emp : employees.values()) {
            if (emp.getName().equals(name)) {
                return emp;
            }
        }
        return null;
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
    public void loadFromFile(String fileName) {
        try {
            URL dataUrl = this.getClass().getResource(fileName);
            if(dataUrl == null) {
                logger.log(System.Logger.Level.ERROR, "Could not find " + fileName + " in resources");
                return;
            }

            File file = new File(dataUrl.toURI());

            try (Scanner scanner = new Scanner(file)) {
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] parts = line.split(",");
                    if (parts.length == 3) {
                        String initials = parts[0].trim();
                        String name = parts[1].trim();
                        boolean isAvailable = Boolean.parseBoolean(parts[2].trim());
                        Employee employee = new Employee(initials, name, isAvailable);
                        save(employee);
                    } else {
                        logger.log(System.Logger.Level.WARNING, "Skipping malformed line in employees.txt: " + line);
                    }
                }
            } catch (Exception e) {
                logger.log(System.Logger.Level.ERROR, "Error reading employees.txt", e);
            }
        } catch (URISyntaxException e) {
            logger.log(System.Logger.Level.ERROR, "Invalid URI syntax for employees.txt", e);
        }
    }

    @Override
    public Map<String, Employee> getEmployees() {
        return employees;
    }

    @Override
    public Set<Employee> getAllAvailableEmployees() {
        Set<Employee> available = new HashSet<>();
        for (Employee emp : employees.values()) {
            if (emp.isAvailable()) available.add(emp);
        }
    return available;
    }
}