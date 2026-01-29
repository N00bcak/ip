package exceptions;


/**
 * Custom exception that activates when the 'find' command does not match any tasks.
 */

public class NoTasksFoundException extends RuntimeException {
    /**
     * Constructs the exception.
     */
    public NoTasksFoundException() {
        super("No tasks were found.");
    }
}
