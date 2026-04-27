package appLogic; //s244813

import java.time.LocalDate;
import java.time.LocalDateTime;

public class AppContext {

    public static final InMemoryEmployeeRepository employeeRepository = new InMemoryEmployeeRepository();
    public static final InMemoryTimeEntryRepository timeEntryRepository = new InMemoryTimeEntryRepository();

    static {
        saveTimeEntry("huba", "Being a good teacher", "TDD/BDD forelæsning", 2.5);
        saveTimeEntry("wilo", "Being a good TA", "Explaining TDD issues", 1.5);
    }

    private static void saveTimeEntry(String initials,
                                      String description,
                                      String summary,
                                      double hours) {

        Employee emp = employeeRepository.findByInitials(initials);

        if (emp == null) {
            System.out.println("Employee not found: " + initials);
            return;
        }

        Activity activity = new WorkActivity(
                initials,
                description,
                summary,
                LocalDate.now()
        );

        emp.addActivity(activity);

        TimeEntry entry = new TimeEntry(
                emp,
                activity,
                LocalDateTime.now(),
                hours
        );

        timeEntryRepository.save(entry);
    }
}