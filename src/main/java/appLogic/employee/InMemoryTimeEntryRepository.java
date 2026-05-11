package appLogic.employee; // Lavet af Artur (s244813)

import java.util.ArrayList;
import java.util.List;

import appLogic.TimeEntry;

public class InMemoryTimeEntryRepository {

    private final List<TimeEntry> entries = new ArrayList<>();

    public void save(TimeEntry entry) {
        entries.add(entry);
    }

    public void remove(TimeEntry entry){
        entries.remove(entry);
    }

    public List<TimeEntry> findAll() {
        return List.copyOf(entries);
    }
}