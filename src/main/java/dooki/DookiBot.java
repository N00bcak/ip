package dooki;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;

import exceptions.CommandIsMissingArgumentException;
import exceptions.NoTasksFoundException;
import exceptions.TaskDescriptionIsEmptyException;
import exceptions.TaskIsMissingArgumentException;
import exceptions.UnsupportedCommandException;
import parser.CommandParser;
import parser.StorageParser;
import storage.Storage;
import tasks.DeadlineTask;
import tasks.EventTask;
import tasks.Task;
import tasks.TaskManager;
import tasks.TodoTask;

/**
 * Encapsulates Dooki's command handling for both CLI and GUI frontends.
 */
public class DookiBot {
    private static final String ERROR_PREFIX = "[ERROR] ";
    private static final String GOODBYE_MESSAGE = "Bye. Hope to see you again soon!";
    private static final String WELCOME_MESSAGE = "Hello! I'm Dooki\nWhat can I do for you?";

    private final TaskManager taskManager;
    private final CommandParser commandParser;
    private boolean isExit;

    /**
     * Creates a Dooki instance backed by on-disk storage.
     */
    public DookiBot() {
        Storage storage = new Storage(new StorageParser());
        assert storage != null : "Storage should be initialized";
        this.taskManager = new TaskManager(storage);
        assert this.taskManager != null : "TaskManager should be initialized";
        this.commandParser = new CommandParser(this.taskManager);
        assert this.commandParser != null : "CommandParser should be initialized";
        this.isExit = false;
    }

    /**
     * Returns the welcome banner shown on startup.
     *
     * @return welcome message.
     */
    public String getWelcomeMessage() {
        return WELCOME_MESSAGE;
    }

    /**
     * Whether the last command requested application exit.
     *
     * @return true if the user asked to exit.
     */
    public boolean shouldExit() {
        return this.isExit;
    }

    /**
     * Produces a response for the given user input.
     *
     * @param input raw user command.
     * @return textual response.
     */
    public String getResponse(String input) {
        String trimmed = input == null ? "" : input.trim();
        if (trimmed.isEmpty()) {
            return "I didn't understand that command. Please try again?";
        }

        String[] tokens = trimmed.split("\\s+", 2);
        String command = tokens[0];

        try {
            switch (command) {
            case "bye":
                this.isExit = true;
                return GOODBYE_MESSAGE;
            case "list":
                return formatTaskList();
            case "sort":
                return handleSort();
            case "delete":
                return handleDelete(trimmed);
            case "mark":
            case "unmark":
                return handleMarking(trimmed);
            case "todo":
                return handleTodo(trimmed);
            case "deadline":
                return handleDeadline(trimmed);
            case "event":
                return handleEvent(trimmed);
            case "find":
                return handleFind(trimmed);
            default:
                throw new UnsupportedCommandException();
            }
        } catch (UnsupportedCommandException e) {
            return ERROR_PREFIX + "I didn't understand that command. Please try again?";
        } catch (TaskDescriptionIsEmptyException e) {
            return ERROR_PREFIX + "Your task has an invalid description :(";
        } catch (TaskIsMissingArgumentException e) {
            return ERROR_PREFIX + "Your task is missing argument " + e.missingArg;
        } catch (DateTimeParseException e) {
            return ERROR_PREFIX + "Your date is invalid. Please use yyyy-MM-dd format.";
        } catch (IllegalArgumentException e) {
            String message = e.getMessage() == null
                    ? "Your command is missing a required argument :("
                    : e.getMessage();
            return ERROR_PREFIX + message;
        } catch (IndexOutOfBoundsException e) {
            return ERROR_PREFIX + "You did not provide a valid task index :(";
        } catch (NoTasksFoundException e) {
            return "There are no matching tasks in your list.";
        } catch (CommandIsMissingArgumentException e) {
            return ERROR_PREFIX + "I can't find it if it doesn't exist :(";
        }
    }

    private String handleDelete(String input) {
        int delIndex = this.commandParser.parseDeleteTask(input);
        Task removed = this.taskManager.delete(delIndex);
        return "Noted. I've removed this task:\n" + removed;
    }

    private String handleSort() {
        this.taskManager.sortByDate();
        return "Sorted tasks by date:\n" + this.taskManager;
    }

    private String handleMarking(String input) {
        int markIndex = this.commandParser.parseMarkOrUnmark(input);
        if (input.startsWith("mark")) {
            this.taskManager.markTaskAsDone(markIndex);
            return "Nice! I've marked this task as done:\n" + this.taskManager.get(markIndex);
        }
        this.taskManager.markTaskAsUndone(markIndex);
        return "OK, I've marked this task as not done yet:\n" + this.taskManager.get(markIndex);
    }

    private String handleTodo(String input) throws TaskDescriptionIsEmptyException {
        HashMap<String, String> taskMap = this.commandParser.parseTodoTask(input);
        assert taskMap.containsKey("desc") && taskMap.get("desc") != null
                : "Todo task description should be present";
        TodoTask newTask = new TodoTask(taskMap.get("desc"));
        this.taskManager.add(newTask);
        return formatTaskAdded(newTask);
    }

    private String handleDeadline(String input)
            throws TaskDescriptionIsEmptyException, TaskIsMissingArgumentException {
        HashMap<String, String> taskMap = this.commandParser.parseDeadlineTask(input);
        assert taskMap.containsKey("desc") && taskMap.containsKey("by")
            : "Deadline task must have desc and by";
        LocalDate deadline = LocalDate.parse(taskMap.get("by"));
        DeadlineTask newTask = new DeadlineTask(taskMap.get("desc"), deadline);
        this.taskManager.add(newTask);
        return formatTaskAdded(newTask);
    }

    private String handleEvent(String input)
            throws TaskDescriptionIsEmptyException, TaskIsMissingArgumentException {
        HashMap<String, String> taskMap = this.commandParser.parseEventTask(input);
        assert taskMap.containsKey("desc") && taskMap.containsKey("from") && taskMap.containsKey("to")
            : "Event task must have desc, from, and to";
        LocalDate from = LocalDate.parse(taskMap.get("from"));
        LocalDate to = LocalDate.parse(taskMap.get("to"));
        EventTask newTask = new EventTask(taskMap.get("desc"), from, to);
        this.taskManager.add(newTask);
        return formatTaskAdded(newTask);
    }

    private String handleFind(String input) {
        String keyword = this.commandParser.parseFindTask(input);
        List<Task> matches = this.taskManager.find(keyword);
        assert matches != null : "Find operation should produce a list";
        return formatFindResults(matches);
    }

    private String formatTaskList() {
        return "Here are the tasks in your list:\n" + this.taskManager;
    }

    private String formatTaskAdded(Task task) {
        return "Got it. I've added this task:\n"
                + task + "\n"
                + "Now you have " + this.taskManager.size() + " tasks in the list.";
    }

    private String formatFindResults(List<Task> matches) {
        StringBuilder builder = new StringBuilder();
        builder.append("Here are the matching tasks in your list:\n");
        for (int i = 0; i < matches.size(); i++) {
            builder.append(i + 1).append(".").append(matches.get(i));
            if (i != matches.size() - 1) {
                builder.append("\n");
            }
        }
        return builder.toString();
    }
}
