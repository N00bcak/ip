package parser;

import java.util.HashMap;

import exceptions.TaskDescriptionIsEmptyException;
import exceptions.TaskIsMissingArgumentException;
import tasks.TaskManager;

/**
 * Provides string processing for Dooki.
 * Will use the internal state of Dooki to make validation checks,
 * and throw errors otherwise.
 */
public class CommandParser {
    private final TaskManager taskManager;

    /**
     * Constructor for CommandParser.
     * @param taskManager The to-be-composed task manager.
     */
    public CommandParser(TaskManager taskManager) {
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

    /**
     * The more eagle-eyed reader will notice this is the same function as above.
     * But it is nevertheless copy-pasted in order to prevent coupling.
     * @param inp Raw input string
     * @return An integer corresponding to the index.
     */
    public int parseDeleteTask(String inp) throws IllegalArgumentException, IndexOutOfBoundsException {
        String[] tokens = inp.split(" ");
        if (tokens.length < 2) {
            throw new IllegalArgumentException("Input should be of format 'delete x'!");
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
    /**
     * Parses a string of the form
     * "todo <arbitrary non-empty string>"
     * If successful, returns a Hashmap containing that string.
     * Adapted: https://stackoverflow.com/questions/26750963/what-does-replace-do-if-no-match-is-found-under-the-hood
     * @param inp Raw input string
     * @return A hashmap containing the title of the task in the "desc" key.
     */
    public HashMap<String, String> parseTodoTask(String inp) throws TaskDescriptionIsEmptyException {
        String arg = inp.replaceFirst("todo", "").strip();
        if (arg.isEmpty()) {
            throw new TaskDescriptionIsEmptyException(inp);
        }
        HashMap<String, String> taskSpec = new HashMap<>();
        taskSpec.put("desc", arg);
        return taskSpec;
    }

    /**
     * Parses a string of the form
     * "deadline <arbitrary non-empty string> /by <arbitrary non-empty string>"
     * If successful, returns a Hashmap containing those 2 strings.
     * Adapted: https://stackoverflow.com/questions/26750963/what-does-replace-do-if-no-match-is-found-under-the-hood
     * @param inp Raw input string
     * @return A hashmap containing:
     *      - the title of the task in the "desc" key.
     *      - the deadline of the task in the "by" key.
     */
    public HashMap<String, String> parseDeadlineTask(
            String inp
    ) throws TaskDescriptionIsEmptyException, TaskIsMissingArgumentException {
        String deadlineString = inp.replaceFirst("deadline", "").strip();
        if (deadlineString.isEmpty()) {
            throw new TaskDescriptionIsEmptyException(inp);
        }
        String[] args = deadlineString.split("/by ");
        if (args.length != 2) {
            throw new TaskIsMissingArgumentException(inp, "/by");
        }
        HashMap<String, String> taskSpec = new HashMap<>();
        taskSpec.put("desc", args[0].strip());
        taskSpec.put("by", args[1].strip());
        return taskSpec;
    }

    /**
     * Parses a string of the form
     * "event <arbitrary non-empty string> /from <arbitrary non-empty string> /to <arbitrary non-empty string>"
     * If successful, returns a Hashmap containing those 3 strings.
     * Adapted: https://stackoverflow.com/questions/26750963/what-does-replace-do-if-no-match-is-found-under-the-hood
     * @param inp Raw input string
     * @return A hashmap containing:
     *      - the title of the task in the "desc" key.
     *      - the start time of the task in the "from" key.
     *      - the end time of the task in the "to" key.
     */
    public HashMap<String, String> parseEventTask(
            String inp
    ) throws TaskDescriptionIsEmptyException, TaskIsMissingArgumentException {
        String eventString = inp.replaceFirst("event", "").strip();
        if (eventString.isEmpty()) {
            throw new TaskDescriptionIsEmptyException(inp);
        }
        String[] args = eventString.split("/from ");
        HashMap<String, String> taskSpec = new HashMap<>();
        taskSpec.put("desc", args[0].strip());
        if (args.length != 2) {
            throw new TaskIsMissingArgumentException(inp, "/from");
        }
        args = args[1].split("/to ");
        if (args.length != 2) {
            throw new TaskIsMissingArgumentException(inp, "/to");
        }
        taskSpec.put("from", args[0].strip());
        taskSpec.put("to", args[1].strip());
        return taskSpec;
    }
}
