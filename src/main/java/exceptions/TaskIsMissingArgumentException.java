package exceptions;

/**
 * Custom exception that activates when a task is missing some required arguments.
 */
public class TaskIsMissingArgumentException extends Exception {
    public final String missingArg;

    /**
     * Instantiates missing argument exception.
     * @param inp Input command.
     * @param arg Missing argument name.
     */
    public TaskIsMissingArgumentException(String inp, String arg) {
        super(inp + " is a task missing required argument " + arg);
        this.missingArg = arg;
    }
}
