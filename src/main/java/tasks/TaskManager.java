package tasks;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import exceptions.NoTasksFoundException;
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
        assert storage != null : "Storage must not be null";
        this.storage = storage;
        this.taskList.addAll(this.storage.load());
    }

    /**
     * Adds a task to the manager.
     * @param task Task instance.
     */
    public void add(Task task) {
        assert task != null : "Task to add must not be null";
        this.taskList.add(task);
        this.persist();
    }

    /**
     * Retrieves a task from the manager.
     * @param taskIndex Index of the task to be retrieved.
     * @return the task at index taskIndex
     */
    public Task get(int taskIndex) {
        assert taskIndex >= 0 && taskIndex < this.taskList.size() : "Task index is out of bounds";
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
     *
     * @param taskIndex the index of the task to be deleted.
     */
    public Task delete(int taskIndex) {
        assert taskIndex >= 0 && taskIndex < this.taskList.size() : "Task index is out of bounds";
        Task removed = this.taskList.remove(taskIndex);
        this.persist();
        return removed;
    }

    /**
     * Reports the number of tasks in the manager's purview.
     *
     * @return an integer denoting the number of tasks.
     */
    public int size() {
        return this.taskList.size();
    }

    /**
     * Finds all tasks with the given keyword in their descriptions.
     *
     * @param keyword given description keyword.
     * @return a list of matching tasks.
     */
    public List<Task> find(String keyword) throws NoTasksFoundException {
        assert keyword != null && !keyword.isBlank() : "Keyword to find must not be blank";
        String lowered = keyword.toLowerCase();
        List<Task> matches = new ArrayList<>();
        for (Task task : this.taskList) {
            if (task.getDescription().toLowerCase().contains(lowered)) {
                matches.add(task);
            }
        }
        if (matches.isEmpty()) {
            throw new NoTasksFoundException();
        }
        return matches;
    }

    /**
     * Sorts tasks chronologically by their date (if present).
     * Tasks without dates stay after dated tasks; equal dates keep their existing order.
     */
    public void sortByDate() {
        this.taskList.sort((taskA, taskB) -> {
            LocalDate dateA = taskA.getSortDate();
            LocalDate dateB = taskB.getSortDate();

            if (dateA == null && dateB == null) {
                return 0;
            }
            if (dateA == null) {
                return 1;
            }
            if (dateB == null) {
                return -1;
            }
            return dateA.compareTo(dateB);
        });
        this.persist();
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
