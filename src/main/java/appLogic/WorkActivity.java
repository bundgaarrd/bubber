package appLogic; //s244813

import java.time.LocalDate;

public class WorkActivity extends Activity {

    public WorkActivity(String name, String description, String summary, LocalDate date) {
        super(name, description, summary, date);
    }

    @Override
    public int getDuration() {
        return 0;
    }
}