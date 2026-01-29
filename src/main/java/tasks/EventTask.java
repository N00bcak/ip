package tasks;

/**
 * Denotes a task with a deadline
 */

public class EventTask extends Task {
    private final String from;
    private final String to;

    /**
     * Constructor for Event task
     * @param description Description of task.
     * @param from start time of task
     * @param to end time of task
     */
    public EventTask(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    public String getFrom() {
        return this.from;
    }

    public String getTo() {
        return this.to;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + this.from + " to: " + this.to + ")";
    }
}
