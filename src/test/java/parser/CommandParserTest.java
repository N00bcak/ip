package parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import exceptions.CommandIsMissingArgumentException;
import exceptions.TaskDescriptionIsEmptyException;
import exceptions.TaskIsMissingArgumentException;
import tasks.TaskManager;
import tasks.TodoTask;

/**
 * Tests for CommandParser. Covered are:
 * - argument validation
 * - index handling.
 */
public class CommandParserTest {
    private static final Path DATA_PATH = Path.of("data", "dooki.txt");

    private CommandParser parser;
    private TaskManager taskManager;

    // Adapted from https://www.baeldung.com/junit-5
    @BeforeEach
    void startTest() throws IOException {
        Files.deleteIfExists(DATA_PATH);
        Files.createDirectories(DATA_PATH.getParent());
        this.taskManager = new TaskManager(new storage.Storage(new StorageParser()));
        this.taskManager.add(new TodoTask("sample"));
        this.parser = new CommandParser(this.taskManager);
    }

    // Adapted from https://www.baeldung.com/junit-5
    @AfterEach
    void endTest() throws IOException {
        Files.deleteIfExists(DATA_PATH);
    }

    @Test
    void parseMarkValidInputReturnsZeroBasedIndex() {
        int idx = parser.parseMarkOrUnmark("mark 1");
        assertEquals(0, idx);
    }

    @Test
    void parseMarkMissingIndexThrowsIllegalArgument() {
        assertThrows(IllegalArgumentException.class, () -> parser.parseMarkOrUnmark("mark"));
    }

    @Test
    void parseMarkOutOfBoundsThrowsIndexOutOfBounds() {
        assertThrows(IndexOutOfBoundsException.class, () -> parser.parseMarkOrUnmark("mark 5"));
    }

    @Test
    void parseDeleteValidInputReturnsZeroBasedIndex() {
        int idx = parser.parseDeleteTask("delete 1");
        assertEquals(0, idx);
    }

    @Test
    void parseTodoEmptyDescriptionThrowsIsEmpty() {
        assertThrows(TaskDescriptionIsEmptyException.class, () -> parser.parseTodoTask("todo   "));
    }

    @Test
    void parseDeadlineMissingByThrowsIsMissing() {
        assertThrows(TaskIsMissingArgumentException.class, () -> parser.parseDeadlineTask("deadline do homework"));
    }

    @Test
    void parseEventMissingFromOrToThrowsIsMissing() {
        assertThrows(TaskIsMissingArgumentException.class, () -> parser.parseEventTask("event party /from tonight"));
        assertThrows(TaskIsMissingArgumentException.class, () -> parser.parseEventTask("event party /to tomorrow"));
    }

    @Test
    void parseFindValidInputReturnsKeyword() {
        String keyword = parser.parseFindTask("find project");
        assertEquals("project", keyword);
    }

    @Test
    void parseFindMissingKeywordThrowsArgumentMissing() {
        assertThrows(CommandIsMissingArgumentException.class, () -> parser.parseFindTask("find   "));
    }
}
