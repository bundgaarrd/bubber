package appLogic.activity;

import appLogic.Activity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ActivityRepository {
    void save(UUID projectId, Activity activity);

    Optional<Activity> findById(UUID activityId);

    Optional<Activity> findByProjectAndName(UUID projectId, String name);

    List<Activity> findByProject(UUID projectId);

    void delete(UUID activityId);
}
