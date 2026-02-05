package tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import exceptions.NoTasksFoundException;
import parser.StorageParser;
import storage.Storage;

/**
 * Tests for TaskManager. Covered are:
 * - mark/unmark
 * - delete
 * - persistence calls.
 */
public class TaskManagerTest {
    private static final Path DATA_PATH = Path.of("data", "dooki.txt");

    // Adapted from https://www.baeldung.com/junit-5
    @BeforeEach
    void startTest() throws IOException {
        Files.deleteIfExists(DATA_PATH);
        Files.createDirectories(DATA_PATH.getParent());
    }

    // Adapted from https://www.baeldung.com/junit-5
    @AfterEach
    void endTest() throws IOException {
        Files.deleteIfExists(DATA_PATH);
    }

    @Test
    void addMarkUnmarkDeleteUpdatesState() {
        Storage storage = new Storage(new StorageParser());
        TaskManager manager = new TaskManager(storage);

        TodoTask todo = new TodoTask("read book");
        manager.add(todo);
        assertEquals(1, manager.size());
        assertFalse(manager.get(0).isDone());

        manager.markTaskAsDone(0);
        assertTrue(manager.get(0).isDone());

        manager.markTaskAsUndone(0);
        assertFalse(manager.get(0).isDone());

        DeadlineTask deadline = new DeadlineTask("return book", LocalDate.parse("2019-12-02"));
        manager.add(deadline);
        assertEquals(2, manager.size());

        Task removed = manager.delete(0);
        assertEquals(todo, removed);
        assertEquals(1, manager.size());
        assertEquals(deadline, manager.get(0));
    }

    @Test
    void findReturnsMatchingTasksCaseInsensitive() {
        Storage storage = new Storage(new StorageParser());
        TaskManager manager = new TaskManager(storage);

        TodoTask todo = new TodoTask("Read book");
        DeadlineTask deadline = new DeadlineTask("return Book", LocalDate.parse("2019-12-02"));
        EventTask event = new EventTask(
            "project meeting",
            LocalDate.parse("2019-12-03"),
            LocalDate.parse("2019-12-04")
        );

        manager.add(todo);
        manager.add(deadline);
        manager.add(event);

        List<Task> matches = manager.find("book");
        assertEquals(2, matches.size());
        assertEquals(todo, matches.get(0));
        assertEquals(deadline, matches.get(1));
    }

    @Test
    void findWithNoMatchesThrowsNoTasksFound() {
        Storage storage = new Storage(new StorageParser());
        TaskManager manager = new TaskManager(storage);

        manager.add(new TodoTask("read book"));

        NoTasksFoundException thrown = assertThrows(NoTasksFoundException.class, () -> manager.find("missing"));
        assertEquals("No tasks were found.", thrown.getMessage());
    }
}
