package appLogic; // s244813

public class AppContext {

    public static final InMemoryEmployeeRepository employeeRepository = new InMemoryEmployeeRepository();
    public static final InMemoryTimeEntryRepository timeEntryRepository = new InMemoryTimeEntryRepository();

    static {
        // det er her at vi kan lave default data til vores inMemory data således at man har noget data der bliver ved selv når man refresher applikationen.
        employeeRepository.save(new Employee("huba", "Hubert Baumeister", true));
        employeeRepository.save(new Employee("wilo", "William Lopez", true));
        employeeRepository.save(new Employee("anda", "Annemette A. Damgaard", true));
        timeEntryRepository.save(new TimeEntry("huba", ));
    }
}