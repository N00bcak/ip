import java.util.HashMap;
import java.util.Scanner;

import display.UI;
import exceptions.TaskDescriptionIsEmptyException;
import exceptions.TaskIsMissingArgumentException;
import exceptions.UnsupportedCommandException;
import parser.Parser;
import tasks.DeadlineTask;
import tasks.EventTask;
import tasks.TaskManager;
import tasks.TodoTask;

/**
 * Entrypoint to chatting with Dooki.
 */

public class Dooki {
    private final TaskManager dookiTasks;
    private final Scanner sc;
    private final UI dookiUI;
    private final Parser parser;

    /**
     * Constructor for Dooki.
     */
    public Dooki() {
        this.dookiTasks = new TaskManager();
        this.sc = new Scanner(System.in);
        this.dookiUI = new UI(this.dookiTasks);
        this.parser = new Parser(this.dookiTasks);
    }

    /**
     * Starts Dooki's main loop.
     */
    public void start() {
        this.dookiUI.showWelcome("Dooki");
        while (true) {
            try {
                String inp = this.sc.nextLine();
                if (inp.equals("bye")) {
                    this.dookiUI.showGoodbye();
                    break;
                } else if (inp.equals("list")) {
                    this.dookiUI.showTasks();
                } else if (inp.startsWith("mark") || inp.startsWith("unmark")) {
                    try {
                        int markIndex = parser.parseMarkOrUnmark(inp);
                        if (inp.startsWith("mark")) {
                            this.dookiTasks.markTaskAsDone(markIndex);
                            this.dookiUI.showTaskMarked(markIndex);
                        } else {
                            this.dookiTasks.markTaskAsUndone(markIndex);
                            this.dookiUI.showTaskUnmarked(markIndex);
                        }
                    } catch (IllegalArgumentException e) {
                        this.dookiUI.showError("'mark'/'unmark' should come with an index :(");
                    } catch (IndexOutOfBoundsException e) {
                        this.dookiUI.showError("You did not provide a valid task index :(");
                    }
                } else if (inp.startsWith("todo")) {
                    try {
                        HashMap<String, String> taskMap = parser.parseTodoTask(inp);
                        TodoTask newTask = new TodoTask(taskMap.get("desc"));
                        this.dookiTasks.add(newTask);
                        this.dookiUI.showTaskAdded(newTask);
                    } catch (TaskDescriptionIsEmptyException e) {
                        this.dookiUI.showError("Your todo task has an invalid description :(");
                    }
                } else if (inp.startsWith("deadline")) {
                    try {
                        HashMap<String, String> taskMap = parser.parseDeadlineTask(inp);
                        DeadlineTask newTask = new DeadlineTask(taskMap.get("desc"), taskMap.get("by"));
                        this.dookiTasks.add(newTask);
                        this.dookiUI.showTaskAdded(newTask);
                    } catch (TaskDescriptionIsEmptyException e) {
                        this.dookiUI.showError("Your deadline task has an invalid description :(");
                    } catch (TaskIsMissingArgumentException e) {
                        this.dookiUI.showError("Your deadline task is missing argument " + e.missingArg);
                    }
                } else if (inp.startsWith("event")) {
                    try {
                        HashMap<String, String> taskMap = parser.parseEventTask(inp);
                        EventTask newTask = new EventTask(taskMap.get("desc"), taskMap.get("from"), taskMap.get("to"));
                        this.dookiTasks.add(newTask);
                        this.dookiUI.showTaskAdded(newTask);
                    } catch (TaskDescriptionIsEmptyException e) {
                        this.dookiUI.showError("Your event task has an invalid description :(");
                    } catch (TaskIsMissingArgumentException e) {
                        this.dookiUI.showError("Your event task is missing argument " + e.missingArg);
                    }
                } else {
                    throw new UnsupportedCommandException();
                }
            } catch (UnsupportedCommandException e) {
                this.dookiUI.showError("I didn't understand that command. Please try again?");
            }
        }
    }

    public static void main(String[] args) {
        Dooki dooki = new Dooki();
        dooki.start();
    }
}
