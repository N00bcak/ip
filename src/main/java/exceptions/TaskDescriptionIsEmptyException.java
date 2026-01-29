package exceptions;

/**
 * Custom exception that activates when a task description is empty
 */

public class TaskDescriptionIsEmptyException extends Exception {
    /**
     * Constructs the exception.
     * @param cmd Full text command
     */
    public TaskDescriptionIsEmptyException(String cmd) {
        super("Parser received command \"" + cmd + "\", which is a task missing a description.");
    }
}
