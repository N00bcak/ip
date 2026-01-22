import java.util.Scanner;

import display.UI;
import tasks.Task;
import tasks.TaskManager;

/**
 * Entrypoint to chatting with Dooki.
 */

public class Dooki {
    private final UI dookiUI;
    private final Scanner sc;
    private final TaskManager dookiTasks;

    /**
     * Constructor for Dooki.
     */
    public Dooki() {
        this.dookiUI = new UI();
        this.sc = new Scanner(System.in);
        this.dookiTasks = new TaskManager();
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
                this.dookiUI.showTasks(this.dookiTasks);
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
