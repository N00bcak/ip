# Dooki User Guide

<!-- Reuse AI generated documentation as noted in `AI.md`-->

Welcome to **Dooki**, a lightweight task assistant that works in both Text UI and JavaFX GUI. Dooki helps you track todos, deadlines, and events; mark or unmark them; find matching tasks; sort them by date; and persist them between sessions.

## Quick Start
- **Requirements:** Java 17+, macOS/Windows/Linux.
- **Run GUI:** `./gradlew run` (opens JavaFX window).
- **Run Text UI:** `java -classpath bin dooki.DookiText` (after `./gradlew build`) or use `./text-ui-test/runtest.sh` for a scripted demo.
- **Data storage:** tasks persist in `data/dooki.txt` relative to the project root.

## Command Summary (Text UI / GUI)
- `list` — show all tasks in order.
- `todo DESCRIPTION` — add a todo.
- `deadline DESCRIPTION /by YYYY-MM-DD` — add a deadline.
- `event DESCRIPTION /from YYYY-MM-DD /to YYYY-MM-DD` — add an event.
- `mark INDEX` / `unmark INDEX` — set completion status (1-based index).
- `delete INDEX` — remove a task.
- `find KEYWORD` — show tasks whose description contains the keyword (case-insensitive).
- `sort` — sort tasks chronologically (dated tasks first; undated after).
- `bye` — exit.

## Using the GUI
1. Start with `./gradlew run`.
2. Type commands into the input box at the bottom and press Enter.
3. Responses and task list updates appear in the chat pane; errors are prefixed with `[ERROR]`.
4. Close the window or type `bye` to exit; tasks are saved automatically.

## Using the Text UI
1. Build: `./gradlew build` (compiles to `build/` and `bin/`).
2. Run: `java -classpath bin dooki.DookiText` then enter commands as above.
3. To run the provided scripted demo: `./text-ui-test/runtest.sh` (compares output against expected).

## Examples
- Add a todo: `todo read book`
- Add a deadline: `deadline return book /by 2026-03-01`
- Add an event: `event project meeting /from 2026-03-02 /to 2026-03-03`
- Mark done: `mark 2`
- Find tasks: `find book`
- Sort by date: `sort`
- Delete a task: `delete 1`

## Notes & Tips
- Indexes shown in `list` and `find` are 1-based.
- Dates must be in `YYYY-MM-DD` format; invalid dates return `[ERROR]` with guidance.
- Sorting uses task dates: events by start date, deadlines by due date, undated tasks come last.
- Data file corruption is handled defensively; invalid lines are reported to stdout during load.

## Troubleshooting
- "I didn't understand that command": check command spelling/format; see the summary above.
- Date errors: ensure valid `YYYY-MM-DD` (e.g., 2026-02-29 is invalid).
- JavaFX launch issues: confirm Java 17+ and that `--module-path` is set (handled by `gradlew run`).

## About
Dooki is a simple task tracker built for the CS2103/T IP. It stores tasks locally and supports both CLI and GUI modes for flexibility.