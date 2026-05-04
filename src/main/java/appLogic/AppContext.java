package appLogic; //s244813

import appLogic.activity.command.CreateWorkActivity;
import appLogic.employee.Employee;
import appLogic.employee.InMemoryEmployeeRepository;
import appLogic.employee.InMemoryTimeEntryRepository;
import appLogic.activity.*;
import appLogic.project.Project;
import appLogic.project.ProjectRegistry;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class AppContext {

    public static final InMemoryEmployeeRepository employeeRepository = new InMemoryEmployeeRepository();
    public static final InMemoryTimeEntryRepository timeEntryRepository = new InMemoryTimeEntryRepository();
    public static final ProjectRegistry projectRegistry = new ProjectRegistry();
    public static Employee loggedInUser;
    public static final ActivityService activityService = new DefaultActivityService(
            new InMemoryActivityRepository(),
            projectRegistry,
            () -> loggedInUser,
            timeEntryRepository
    );

    static {
        employeeRepository.save(new Employee("huba", "Hubert Baumeister", true));
        employeeRepository.save(new Employee("wilo", "William Lopez", true));
        employeeRepository.save(new Employee("anda", "Annemette A. Damgaard", true));
        loggedInUser = employeeRepository.findByInitials("huba");

        Project contextProject = projectRegistry.createProject("AppContext");
        contextProject.assignProjectLeader(loggedInUser);

        saveTimeEntry(contextProject.getProjectID(), "huba", "Being a good teacher", "TDD/BDD forelæsning", 2.5);
        saveTimeEntry(contextProject.getProjectID(), "wilo", "Being a good TA", "Explaining TDD issues", 1.5);
    }

    private static void saveTimeEntry(String projectId,
                                      String initials,
                                      String description,
                                      String summary,
                                      double hours) {

        Employee emp = employeeRepository.findByInitials(initials);

        if (emp == null) {
            System.out.println("Employee not found: " + initials);
            return;
        }

        Activity activity = activityService.createWorkActivity(new CreateWorkActivity(
                UUID.fromString(projectId),
                initials + "-" + description,
                description,
                summary,
                LocalDate.now()
        ));

        activityService.registerWork(activity.getId(), emp, LocalDateTime.now(), hours);
    }
}
