package appLogic;

import java.time.LocalDate;

public class WorkActivity extends Activity {

    public WorkActivity(String description, String summary, LocalDate date) {
        super(description, summary, date);
    }

    @Override
    public int getDuration() {
        return 0;
    }
}