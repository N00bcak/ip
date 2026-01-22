import java.util.Scanner;

import display.UI;
import parser.Parser;
import tasks.Task;
import tasks.TaskManager;

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
            String inp = this.sc.nextLine();
            if (inp.equals("bye")) {
                this.dookiUI.showGoodbye();
                break;
            } else if (inp.equals("list")) {
                this.dookiUI.showTasks();
            } else if (inp.startsWith("mark ") || inp.startsWith("unmark ")) {
                try {
                    int markIndex = parser.parseMarkOrUnmark(inp);
                    if (inp.startsWith("mark ")) {
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
            } else {
                Task newTask = new Task(inp);
                this.dookiTasks.add(newTask);
                this.dookiUI.showTaskAdded(newTask);
            }
        }
    }

    public static void main(String[] args) {
        Dooki dooki = new Dooki();
        dooki.start();
    }
}
