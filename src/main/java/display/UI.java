package display;

import tasks.Task;
import tasks.TaskManager;

/**
 * Handles all interactions to the user (i.e. printing stuff via the text interface).
 */

public class UI {
    // Adapted output format from https://nus-cs2103-ay2526-s2.github.io/website/schedule/week2/project.html
    private static final String LONG_LINE = "_".repeat(30);

    private void showLine() {
        System.out.println(LONG_LINE);
    }

    private void showMessage(String message) {
        this.showLine();
        System.out.println(message);
        this.showLine();
    }

    /**
     * Greets the user.
     *
     * @param name The name to greet the user as.
     */
    public void showWelcome(String name) {
        this.showMessage(
            "Hello! I'm " + name + "\n"
            + "What can I do for you?"
        );
    }

    /**
     * Bids the user farewell.
     */
    public void showGoodbye() {
        this.showMessage("Bye. Hope to see you again soon!");
    }

    /**
     * Prints a message to confirm that the task has been added.
     * @param task The task to print
     */
    public void showTaskAdded(Task task) {
        this.showMessage(
            "added: " + task
        );
    }

    /**
     * Prints the list of tasks the user has right now.
     * @param taskManager the task manager to retrieve tasks from.
     */
    public void showTasks(TaskManager taskManager) {
        this.showMessage(taskManager.toString());
    }

}
