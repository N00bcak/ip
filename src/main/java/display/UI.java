package display;

import tasks.Task;
import tasks.TaskManager;

/**
 * Handles all interactions to the user (i.e. printing stuff via the text interface).
 */

public class UI {
    // Adapted output format from https://nus-cs2103-ay2526-s2.github.io/website/schedule/week2/project.html
    private static final String LONG_LINE = "_".repeat(30);
    private final TaskManager taskManager;

    public UI(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    private void showLine() {
        System.out.println(LONG_LINE);
    }

    private void showMessage(String message) {
        this.showLine();
        System.out.println(message);
        this.showLine();
    }

    /**
     * Displays an error to the user
     * @param message The error message
     */
    public void showError(String message) {
        this.showMessage("[ERROR] " + message);
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
            "Got it. I've added this task:\n"
            + task + "\n"
            + "Now you have " + this.taskManager.size() + " tasks in the list."
        );
    }

    /**
     * Prints a message to confirm that the task has been marked as done.
     * @param taskIndex The index of the marked task.
     */
    public void showTaskMarked(int taskIndex) {
        this.showMessage(
            "Nice! I've marked this task as done:\n"
            + this.taskManager.get(taskIndex)
        );

    }

    /**
     * Prints a message to confirm that the task has been marked as undone.
     * @param taskIndex The index of the marked task.
     */
    public void showTaskUnmarked(int taskIndex) {
        this.showMessage(
            "OK, I've marked this task as not done yet:\n"
            + this.taskManager.get(taskIndex)
        );
    }

    /**
     * Prints a message to confirm that the task has been deleted.
     * @param taskIndex The index of the deleted task.
     */
    public void showTaskDeleted(int taskIndex) {
        this.showMessage(
            "Noted. I've removed this task:\n"
            + this.taskManager.get(taskIndex)
        );
    }

    /**
     * Prints the list of tasks the user has right now.
     */
    public void showTasks() {
        this.showMessage(
            "Here are the tasks in your list:\n"
            + taskManager.toString()
        );
    }

}
