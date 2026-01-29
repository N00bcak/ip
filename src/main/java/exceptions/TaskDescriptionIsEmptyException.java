package exceptions;

/**
 * Custom exception that activates when a task description is empty
 */
public class TaskDescriptionIsEmptyException extends Exception {
    /**
     * Creates an exception indicating the provided command lacked a description.
     * @param cmd The original command string.
     */
    public TaskDescriptionIsEmptyException(String cmd) {
        super("Parser received command \"" + cmd + "\", which is a task missing a description.");
    }
}
