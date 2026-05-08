package appLogic.activity;

import appLogic.activity.impl.Activity;

import java.util.*;

public class InMemoryActivityRepository implements ActivityRepository {
    private final Map<UUID, Activity> byId = new HashMap<>();
    private final Map<String, Set<UUID>> byProject = new HashMap<>();

    @Override
    public void save(String projectId, Activity activity) {
        byId.put(activity.getId(), activity);
        byProject.computeIfAbsent(projectId, ignored -> new HashSet<>()).add(activity.getId());
    }

    @Override
    public Optional<Activity> findById(UUID activityId) {
        return Optional.ofNullable(byId.get(activityId));
    }

    @Override
    public Optional<Activity> findByProjectAndName(String projectId, String name) {
        return findByProject(projectId).stream().filter(activity -> activity.getName().equals(name)).findFirst();
    }

    @Override
    public List<Activity> findByProject(String projectId) {
        Set<UUID> ids = byProject.getOrDefault(projectId, Set.of());
        List<Activity> activities = new ArrayList<>();
        for (UUID id : ids) {
            Activity activity = byId.get(id);
            if (activity != null) {
                activities.add(activity);
            }
        }
        return activities;
    }

    @Override
    public void delete(UUID activityId) {
        byId.remove(activityId);
        for (Set<UUID> ids : byProject.values()) {
            ids.remove(activityId);
        }
    }
}
