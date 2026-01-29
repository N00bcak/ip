package parser;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import exceptions.ParseStorageLineFailureException;
import tasks.DeadlineTask;
import tasks.EventTask;
import tasks.Task;
import tasks.TodoTask;

/**
 * Provides string processing for Dooki.
 * Will use the internal state of Dooki to make validation checks,
 * and throw errors otherwise.
 */
public class StorageParser {

    public StorageParser() {
    }

    /**
     * Parses a line (describing a task) from storage.Storage
     * @param line The line from storage.Storage
     * @return The task represented by the line.
     */
    public Task parseStorageLine(String line) throws ParseStorageLineFailureException {
        if (line == null || line.isBlank()) {
            throw new ParseStorageLineFailureException("<<Line was empty or null>>");
        }
        /*
        Explanation: Split by:
            - any sequence of whitespace
            - followed by |
            - any sequence of whitespace
        in that exact order.
        */
        String[] parts = line.split("\\s*\\|\\s*");
        if (parts.length < 3) {
            throw new ParseStorageLineFailureException("Task '" + line + "' has fewer than 3 parts");
        }
        String type = parts[0];
        boolean isDone = parts[1].equals("1");
        String description = parts[2];

        Task task;
        if (type.equals("T")) {
            task = new TodoTask(description);
        } else if (type.equals("D")) {
            if (parts.length < 4) {
                throw new ParseStorageLineFailureException("DeadlineTask '" + line + "' has no 'by' field");
            }
            try {
                LocalDate deadline = LocalDate.parse(parts[3]);
                task = new DeadlineTask(description, deadline);
            } catch (DateTimeParseException e) {
                throw new ParseStorageLineFailureException("DeadlineTask '" + line + "' has invalid date");
            }
        } else if (type.equals("E")) {
            if (parts.length < 5) {
                throw new ParseStorageLineFailureException("EventTask '" + line + "' has no 'from' or 'to' field");
            }
            try {
                LocalDate from = LocalDate.parse(parts[3]);
                LocalDate to = LocalDate.parse(parts[4]);
                task = new EventTask(description, from, to);
            } catch (DateTimeParseException e) {
                throw new ParseStorageLineFailureException("EventTask '" + line + "' has invalid date(s)");
            }
        } else {
            throw new ParseStorageLineFailureException("Received unknown task type");
        }

        if (isDone) {
            task.markDone();
        }
        return task;
    }
}
