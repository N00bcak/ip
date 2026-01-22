package tasks;
/**
 * Denotes a task that user provides.
 */

public class Task {
    private final String description;

    /**
     * Instantiates a task.
     * Note that tasks start off as undone.
     * @param description Describes the task
     */
    public Task(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}
