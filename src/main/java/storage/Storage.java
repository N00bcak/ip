package storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import exceptions.ParseStorageLineFailureException;
import parser.StorageParser;
import tasks.DeadlineTask;
import tasks.EventTask;
import tasks.Task;
import tasks.TodoTask;

/**
 * Maintains Dooki's internal state via text file.
 * OS-independent filepaths inspired by https://stackoverflow.com/questions/54588935
 */
public class Storage {
    private final Path storagePath;
    private final StorageParser storageParser;

    /**
     * Loads a file pointer pointing to Dooki's save location.
     * Note: We assume (as allowed by https://nus-cs2103-ay2526-s2.github.io/website/schedule/week3/project.html)
     * that we can hard-code the relative file path.
     * It is expected that we invoke Dooki from the project root, except when testing.
     * @param storageParser Storage Parser companion object.
     */
    public Storage(StorageParser storageParser) {
        this.storagePath = Paths.get("data", "dooki.txt");
        this.storageParser = storageParser;
    }

    /**
     * Simple check to make sure the storage path exists.
     * Failing which, the file will be created.
     * @throws IOException If the subsequent file operations have errors, we throw IOException.
     */
    private void ensureFileExists() throws IOException {
        Path parent = this.storagePath.getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }
        if (!Files.exists(this.storagePath)) {
            Files.createFile(this.storagePath);
        }
    }

    /**
     * Loads tasks from disk. If the file (or its parents) do not exist,
     * they will be created and an empty list is returned.
     * @return List of tasks read from disk.
     */
    public ArrayList<Task> load() {
        ArrayList<Task> tasks = new ArrayList<>();
        try {
            ensureFileExists();
            List<String> lines = Files.readAllLines(this.storagePath);
            for (String line : lines) {
                Task task = this.storageParser.parseStorageLine(line);
                tasks.add(task);
            }
        } catch (IOException e) {
            System.out.println("Something went wrong while loading the file: " + e.getMessage());
        } catch (ParseStorageLineFailureException e) {
            System.out.println("It would seem that your file is malformed. You may want to check it out.");
        }
        return tasks;
    }

    /**
     * Saves the tasks to disk. Always overwrites existing file.
     * @param tasks List of tasks written to disk.
     */
    public void save(List<Task> tasks) {
        try {
            ensureFileExists();
            List<String> lines = tasks.stream()
                .map(this::formatTask)
                .collect(Collectors.toList());
            Files.write(this.storagePath, lines);
        } catch (IOException e) {
            System.out.println("Something went wrong while saving the file: " + e.getMessage());
        }
    }

    /**
     * Formats the task for export into a save file.
     * Despite
     * @param task The task to be exported
     * @return A string describing the task.
     */
    private String formatTask(Task task) {
        String status = task.isDone() ? "1" : "0";
        // Reuse from IDE suggestion to invoke instanceof.
        if (task instanceof TodoTask) {
            return String.join(
                " | ",
                "T",
                status,
                task.getDescription()
            );
        }
        if (task instanceof DeadlineTask deadlineTask) {
            return String.join(
                " | ",
                "D",
                status,
                task.getDescription(),
                deadlineTask.getDeadline()
            );
        }
        if (task instanceof EventTask eventTask) {
            return String.join(
                " | ",
                "E",
                status,
                task.getDescription(),
                eventTask.getFrom(),
                eventTask.getTo()
            );
        }

        return String.join(" | ", "?", status, task.getDescription());
    }
}
