package parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import exceptions.ParseStorageLineFailureException;
import tasks.DeadlineTask;
import tasks.EventTask;
import tasks.Task;
import tasks.TodoTask;

/**
 * Tests for StorageParser. Covered are:
 * - parsing of different task types
 * - error handling
 */
public class StorageParserTest {

    private final StorageParser parser = new StorageParser();

    @Test
    void parseTodoTaskSuccess() throws ParseStorageLineFailureException {
        Task task = parser.parseStorageLine("T | 0 | read book");
        TodoTask todo = (TodoTask) task;
        assertEquals("read book", todo.getDescription());
    }

    @Test
    void parseDeadlineTaskSuccessParsesDate() throws ParseStorageLineFailureException {
        Task task = parser.parseStorageLine("D | 1 | return book | 2019-12-02");
        DeadlineTask deadline = (DeadlineTask) task;
        assertEquals(LocalDate.parse("2019-12-02"), deadline.getDeadline());
        assertEquals("return book", deadline.getDescription());
        assertEquals("X", deadline.getDoneSymbol());
    }

    @Test
    void parseEventTaskSuccessParsesDates() throws ParseStorageLineFailureException {
        Task task = parser.parseStorageLine("E | 0 | project meeting | 2019-12-03 | 2019-12-04");
        EventTask event = (EventTask) task;
        assertEquals(LocalDate.parse("2019-12-03"), event.getFrom());
        assertEquals(LocalDate.parse("2019-12-04"), event.getTo());
        assertEquals("project meeting", event.getDescription());
    }

    @Test
    void parseEventTaskInvalidDateThrowsFailure() {
        assertThrows(ParseStorageLineFailureException.class, () ->
            parser.parseStorageLine("E | 0 | bad dates | not-a-date | 2019-12-04")
        );
    }
}
