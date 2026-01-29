package tasks;

import java.util.ArrayList;

import storage.Storage;

/**
 * Manages the user's list of tasks.
 */
public class TaskManager {
    private final ArrayList<Task> taskList = new ArrayList<>();
    // Compose storage so we can autosync on every iteration.
    private final Storage storage;

    /**
     * Initializes the task manager with existing storage.
     * @param storage The storage location
     */
    public TaskManager(Storage storage) {
        this.storage = storage;
        this.taskList.addAll(this.storage.load());
    }

    /**
     * Adds a task to the manager.
     * @param task Task instance.
     */
    public void add(Task task) {
        this.taskList.add(task);
        this.persist();
    }

    /**
     * Retrieves a task from the manager.
     * @param taskIndex Index of the task to be retrieved.
     * @return the task at index taskIndex
     */
    public Task get(int taskIndex) {
        return this.taskList.get(taskIndex);
    }

    /**
     * Marks a task as done.
     * @param taskIndex the index of the task to be marked as done.
     */
    public void markTaskAsDone(int taskIndex) {
        this.get(taskIndex).markDone();
        this.persist();
    }

    /**
     * Marks a task as undone.
     * @param taskIndex the index of the task to be marked as undone.
     */
    public void markTaskAsUndone(int taskIndex) {
        this.get(taskIndex).markUndone();
        this.persist();
    }

    /**
     * Marks a task for deletion.
     * @param taskIndex the index of the task to be deleted.
     */
    public void delete(int taskIndex) {
        this.taskList.remove(taskIndex);
        this.persist();
    }

    /**
     * Report the number of tasks in the manager's purview
     * @return An integer denoting the number of tasks.
     */
    public int size() {
        return this.taskList.size();
    }

    private void persist() {
        if (this.storage != null) {
            this.storage.save(this.taskList);
        }
    }

    @Override
    public String toString() {
        if (this.size() == 0) {
            return " (no tasks yet)";
        } else {
            StringBuilder lst = new StringBuilder();
            for (int i = 0; i < this.size(); i++) {
                lst.append((i + 1));
                lst.append(".");
                lst.append(this.get(i));
                if (i != this.size() - 1) {
                    lst.append("\n");
                }
            }
            return lst.toString();
        }
    }
}
