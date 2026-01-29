package exceptions;

/**
 * Custom exception that activates when Storage.parseLine is unable to parse the given line.
 */
public class ParseStorageLineFailureException extends Exception {
    /**
     * Constructs the exception.
     * @param message Custom error message
     */
    public ParseStorageLineFailureException(String message) {
        super(
            "Storage line provided could not be parsed: '" + message + "'.\n"
            + "It is possible your task list was corrupted."
        );
    }
}
