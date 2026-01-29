package exceptions;

/**
 * Custom exception that activates when a command is unsupported.
 */

public class UnsupportedCommandException extends Exception {
    /**
     * Constructs the exception.
     */
    public UnsupportedCommandException() {
        super("The what now? Sorry I don't understand what that means you need to wait for next week.");
    }
}
