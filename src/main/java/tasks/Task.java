package tasks;

import java.time.LocalDate;

/**
 * Denotes a task provided by the user.
 * A task can be done or not done; concrete subclasses represent specific task types.
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
        this(description, false);
    }

    protected Task(String description, boolean done) {
        this.description = (description == null) ? "" : description;
        this.done = done;
    }

    /**
     * Mark the task as done.
     */
    public void markDone() {
        this.done = true;
    }

    /**
     * Mark the task as not done.
     */
    public void markUndone() {
        this.done = false;
    }

    /**
     * Reports whether the task is done.
     * @return true if marked done, false otherwise.
     */
    public boolean isDone() {
        return this.done;
    }

    public String getDescription() {
        return this.description;
    }

    /**
     * Returns the date used for chronological sorting.
     * Subclasses without an inherent date return null to indicate they should be ordered after dated tasks.
     * @return a LocalDate for sorting, or null if the task has no date.
     */
    public LocalDate getSortDate() {
        return null;
    }

    public String getDoneSymbol() {
        return this.done ? "X" : " ";
    }

    @Override
    public String toString() {
        return "[" + this.getDoneSymbol() + "] " + this.description;
    }
}
