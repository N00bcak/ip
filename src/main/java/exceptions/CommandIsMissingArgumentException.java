package exceptions;

/**
 * Custom exception that activates when a command is missing a necessary argument
 */

public class CommandIsMissingArgumentException extends RuntimeException {
    public CommandIsMissingArgumentException(String cmd) {
        super("Parser received command \"" + cmd + "\", which is missing a required argument.");
    }
}
