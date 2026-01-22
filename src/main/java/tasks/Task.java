package tasks;
/**
 * Denotes a task that user provides.
 * A task can be done or not done.
 * This is a base class and should no longer be instantiated.
 * ... is what I gather from reading the specification, which is
 * awfully vague about this matter
 */

public abstract class Task {
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
