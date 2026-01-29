package tasks;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Denotes a task with a specified start and end date.
 */
public class EventTask extends Task {
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy");
    private final LocalDate from;
    private final LocalDate to;

    /**
     * Constructor for Event task.
     * @param description Description of task.
     * @param from Start date of task.
     * @param to End date of task.
     */
    public EventTask(String description, LocalDate from, LocalDate to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    public LocalDate getFrom() {
        return this.from;
    }

    public LocalDate getTo() {
        return this.to;
    }

    @Override
    public String toString() {
        String formattedFrom = this.from.format(OUTPUT_FORMAT);
        String formattedTo = this.to.format(OUTPUT_FORMAT);
        return "[E]" + super.toString() + " (from: " + formattedFrom + " to: " + formattedTo + ")";
    }
}
