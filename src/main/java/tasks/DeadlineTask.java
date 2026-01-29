package tasks;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Denotes a task with a deadline.
 */
public class DeadlineTask extends Task {
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy");
    private final LocalDate deadline;

    /**
     * Constructor for DeadlineTask.
     * @param description Description of task.
     * @param deadline Deadline of task as LocalDate.
     */
    public DeadlineTask(String description, LocalDate deadline) {
        super(description);
        this.deadline = deadline;
    }

    public LocalDate getDeadline() {
        return this.deadline;
    }

    @Override
    public String toString() {
        // Reused from https://nus-cs2103-ay2526-s2.github.io/website/schedule/week3/project.html
        String formattedDeadline = this.deadline.format(OUTPUT_FORMAT);
        return "[D]" + super.toString() + " (by: " + formattedDeadline + ")";
    }
}
