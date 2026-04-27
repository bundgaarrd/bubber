package appLogic;

import java.util.ArrayList;
import java.util.List;

public class InMemoryTimeEntryRepository {

    private final List<TimeEntry> entries = new ArrayList<>();

    public void save(TimeEntry entry) {
        entries.add(entry);
    }

    public List<TimeEntry> findAll() {
        return List.copyOf(entries);
    }
}