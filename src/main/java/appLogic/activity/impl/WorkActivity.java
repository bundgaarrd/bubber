package appLogic.activity.impl; //s244813

import java.time.LocalDate;

public class WorkActivity extends Activity {

    public WorkActivity(String name, String description, String summary, LocalDate date) {
        super(name, description, summary, date, null);
    }

    @Override
    public int getDuration() {
        return 0;
    }
}