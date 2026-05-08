package appLogic.report;

import appLogic.employee.InMemoryTimeEntryRepository;
import appLogic.activity.*;
import appLogic.activity.impl.Activity;
import appLogic.TimeEntry;
import appLogic.project.Project;
import appLogic.project.ProjectRegistry;
import appLogic.Summary;

import java.util.List;
import java.util.Set;
import java.util.HashSet;

public class ReportService {
    private final ProjectRegistry projectRegistry;
    private final ActivityRepository activityRepository;
    private final InMemoryTimeEntryRepository timeEntryRepository;

    public ReportService(ProjectRegistry projectRegistry, 
                         ActivityRepository activityRepository, 
                         InMemoryTimeEntryRepository timeEntryRepository) {
        this.projectRegistry = projectRegistry;
        this.activityRepository = activityRepository;
        this.timeEntryRepository = timeEntryRepository;
    }


    public Report generateReport(String projectId) {
        Project project = projectRegistry.getProjectById(projectId);
        if (project == null) {
            throw new IllegalArgumentException("Project not found: " + projectId);
    }
        List<Activity> activities = activityRepository.findByProject(projectId);
        List<TimeEntry> allEntries = timeEntryRepository.findAll();

        Set<Summary> summaries = new HashSet<>();
        double totalHoursUsed = 0;

        for (Activity activity : activities) {
            double activityHours = allEntries.stream()
                .filter(e -> e.getActivity().getId().equals(activity.getId()))
                .mapToDouble(TimeEntry::getHoursWorked)
                .sum();
            totalHoursUsed += activityHours;
            summaries.add(new Summary(activityHours, activity.getName()));
        }

        int remainingHours = Math.max(0, project.getExpectedHours() - (int) totalHoursUsed);
        return new Report(totalHoursUsed, summaries, remainingHours);
    }
}
