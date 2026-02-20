package parser;

import java.util.HashMap;

import exceptions.CommandIsMissingArgumentException;
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
        assert taskManager != null : "TaskManager must not be null";
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
        assert inp != null : "Mark/Unmark input should not be null";
        return parseIndexedCommand(inp, "Input should be of format 'mark x' or 'unmark x'!");
    }

    /**
     * The more eagle-eyed reader will notice this is the same function as above.
     * But it is nevertheless copy-pasted in order to prevent coupling.
     * @param inp Raw input string
     * @return An integer corresponding to the index.
     */
    public int parseDeleteTask(String inp) throws IllegalArgumentException, IndexOutOfBoundsException {
        assert inp != null : "Delete input should not be null";
        return parseIndexedCommand(inp, "Input should be of format 'delete x'!");
    }
    /**
    * Parses a string of the form {@code "todo DESCRIPTION"} where DESCRIPTION is non-empty.
    * If successful, returns a Hashmap containing that string.
    * Adapted: https://stackoverflow.com/questions/26750963/what-does-replace-do-if-no-match-is-found-under-the-hood
    * @param inp Raw input string
    * @return A hashmap containing the title of the task in the "desc" key.
     */
    public HashMap<String, String> parseTodoTask(String inp) throws TaskDescriptionIsEmptyException {
        assert inp != null : "Todo input should not be null";
        String arg = inp.replaceFirst("todo", "").strip();
        if (arg.isEmpty()) {
            throw new TaskDescriptionIsEmptyException(inp);
        }
        HashMap<String, String> taskSpec = new HashMap<>();
        taskSpec.put("desc", arg);
        return taskSpec;
    }

    /**
    * Parses a string of the form {@code "deadline DESCRIPTION /by DEADLINE"} with both parts non-empty.
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
        assert inp != null : "Deadline input should not be null";
        String deadlineString = inp.replaceFirst("deadline", "").strip();
        String[] args = deadlineString.split("/by ");
        if (args.length != 2) {
            throw new TaskIsMissingArgumentException(inp, "/by");
        }
        HashMap<String, String> taskSpec = new HashMap<>();
        String desc = args[0].strip();
        if (desc.isEmpty()) {
            throw new TaskDescriptionIsEmptyException(inp);
        }
        taskSpec.put("desc", desc);
        taskSpec.put("by", args[1].strip());
        return taskSpec;
    }

    /**
    * Parses a string of the form {@code "event DESCRIPTION /from START /to END"} with all parts non-empty.
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
        assert inp != null : "Event input should not be null";
        String eventString = inp.replaceFirst("event", "").strip();
        String[] args = eventString.split("/from ");
        HashMap<String, String> taskSpec = new HashMap<>();
        String desc = args[0].strip();
        if (desc.isEmpty()) {
            throw new TaskDescriptionIsEmptyException(inp);
        }
        taskSpec.put("desc", desc);
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

    /**
     * Parses a string of the form {@code "find KEYWORD"} where KEYWORD is non-empty.
     * Note that this string will be used as a keyword to search for tasks.
     * @param inp Raw input string.
     * @return the keyword to search for.
     * @throws CommandIsMissingArgumentException if keyword is missing or blank.
     */
    public String parseFindTask(String inp) throws CommandIsMissingArgumentException {
        assert inp != null : "Find input should not be null";
        String keyword = inp.replaceFirst("find", "").strip();
        if (keyword.isEmpty()) {
            throw new CommandIsMissingArgumentException(inp);
        }
        return keyword;
    }

    private int parseIndexedCommand(String inp, String usageMessage)
            throws IllegalArgumentException, IndexOutOfBoundsException {
        String[] tokens = inp.split(" ");
        if (tokens.length < 2) {
            throw new IllegalArgumentException(usageMessage);
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
