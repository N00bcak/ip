package parser;

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
            task = new DeadlineTask(description, parts[3]);
        } else if (type.equals("E")) {
            if (parts.length < 5) {
                throw new ParseStorageLineFailureException("EventTask '" + line + "' has no 'from' or 'to' field");
            }
            task = new EventTask(description, parts[3], parts[4]);
        } else {
            throw new ParseStorageLineFailureException("Received unknown task type");
        }

        if (isDone) {
            task.markDone();
        }
        return task;
    }
}
