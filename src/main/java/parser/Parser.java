package parser;

import tasks.TaskManager;

/**
 * Provides string processing for Dooki.
 * Will use the internal state of Dooki to make validation checks,
 * and throw errors otherwise.
 */
public class Parser {
    private final TaskManager taskManager;

    public Parser(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    /**
     * Parses a string of the form:
     * - "mark x", or
     * - "unmark x".
     * If successful, returns x as an integer.
     * @param inp Raw input string
     * @return An integer corresponding to the index.
     */
    public int parseMarkOrUnmark(String inp) throws IllegalArgumentException, IndexOutOfBoundsException {
        String[] tokens = inp.split(" ");
        if (tokens.length < 2) {
            throw new IllegalArgumentException("Input should be of format 'mark x' or 'unmark x'!");
        }
        /*
         Note: The website specification implies the list uses 1-indexing
         (as far as the user is concerned)
         So we must compensate.
        */
        int index = Integer.parseInt(tokens[1]) - 1;
        if (index < 0 || index >= this.taskManager.size()) {
            throw new IndexOutOfBoundsException("Invalid task number.");
        }
        return index;
    }
}
