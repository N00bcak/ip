package tasks;
/**
 * Denotes a task to be done. This seems to be the vanilla task.
 */

public class TodoTask extends Task {

    /**
     * Constructor for TodoTask
     * @param description Description of task.
     */
    public TodoTask(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
