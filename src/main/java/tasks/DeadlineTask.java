package tasks;
/**
 * Denotes a task with a deadline
 */

public class DeadlineTask extends Task {
    private final String deadline;

    /**
     * Constructor for DeadlineTask
     * @param description Description of task.
     * @param deadline Deadline of task.
     */
    public DeadlineTask(String description, String deadline) {
        super(description);
        this.deadline = deadline;
    }

    public String getDeadline() {
        return this.deadline;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + this.deadline + ")";
    }
}
