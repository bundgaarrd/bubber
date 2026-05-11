package appLogic.activity;

import appLogic.activity.impl.Activity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ActivityRepository {
    void save(String projectId, Activity activity);

    Optional<Activity> findById(UUID activityId);

    Optional<Activity> findByProjectAndName(String projectId, String name);

    List<Activity> findByProject(String projectId);

    void delete(UUID activityId);
}
