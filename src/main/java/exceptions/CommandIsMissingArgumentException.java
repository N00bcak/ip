package exceptions;

/**
 * Custom exception that activates when a command is missing a necessary argument
 */
public class CommandIsMissingArgumentException extends RuntimeException {
    /**
     * Constructs the exception.
     * @param cmd Full text command.
     */
    public CommandIsMissingArgumentException(String cmd) {
        super("Parser received command \"" + cmd + "\", which is missing a required argument.");
    }
}
