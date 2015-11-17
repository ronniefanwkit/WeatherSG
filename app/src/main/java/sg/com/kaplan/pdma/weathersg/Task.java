package sg.com.kaplan.pdma.weathersg;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("Task")
public class Task extends ParseObject {
    public Task(){}

    public boolean isCompleted() {
        return getBoolean("completed");
    }

    public void setCompleted(boolean completed) {
        put("completed", completed);
    }

    public String getDescription() {
        return getString("description");
    }

    public void setDescription(String description) {
        put("description", description);
    }
}

