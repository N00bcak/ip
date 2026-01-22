package tasks;
/**
 * Denotes a task that user provides.
 * A task can be done or not done.
 */

public class Task {
    private final String description;
    private boolean done;

    /**
     * Instantiates a task.
     * Note that tasks start off as undone.
     * @param description Describes the task
     */
    public Task(String description) {
        this.description = description;
        this.done = false;
    }

    public void markDone() {
        this.done = true;
    }

    public void markUndone() {
        this.done = false;
    }

    public String getDoneSymbol() {
        return this.done ? "X" : " ";
    }

    @Override
    public String toString() {
        return "[" + this.getDoneSymbol() + "] " + this.description;
    }
}
