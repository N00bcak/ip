package tasks;

import java.util.ArrayList;

/**
 * Manages the user's list of tasks.
 */
public class TaskManager {
    private final ArrayList<Task> taskList = new ArrayList<>();

    /**
     * Adds a task to the manager.
     * @param task Task instance.
     */
    public void add(Task task) {
        this.taskList.add(task);
    }

    /**
     * Retrieves a task from the manager.
     * @param taskIndex Index of the task to be retrieved.
     * @return the task at index taskIndex
     */
    public Task get(int taskIndex) {
        return this.taskList.get(taskIndex);
    }

    public void markTaskAsDone(int taskIndex) {
        this.get(taskIndex).markDone();
    }

    public void markTaskAsUndone(int taskIndex) {
        this.get(taskIndex).markUndone();
    }

    /**
     * Report the number of tasks in the manager's purview
     * @return An integer denoting the number of tasks.
     */
    public int size() {
        return this.taskList.size();
    }

    @Override
    public String toString() {
        if (this.size() == 0) {
            return " (no tasks yet)";
        } else {
            StringBuilder lst = new StringBuilder();
            for (int i = 0; i < this.size(); i++) {
                lst.append((i + 1));
                lst.append(". ");
                lst.append(this.get(i));
                if (i != this.size() - 1) {
                    lst.append("\n");
                }
            }
            return lst.toString();
        }
    }
}
