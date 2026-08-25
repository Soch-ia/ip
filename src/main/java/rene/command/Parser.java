package rene.command;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import rene.exception.ReneException;
import rene.task.Deadline;
import rene.task.Event;
import rene.task.Task;
import rene.task.Todo;

/**
 * Converts user input into commands, tasks, and task numbers Rene can process.
 */
public class Parser {
    private static final String UNKNOWN_COMMAND_MESSAGE =
            "I don't know that command yet. Try todo, deadline, event, list, mark, unmark, delete, find, or bye.";

    /**
     * Creates a parser for Rene's supported command syntax.
     */
    public Parser() {
    }

    /**
     * Identifies the command type and separates its argument from its keyword.
     *
     * @param input the complete line entered by the user.
     * @return the parsed command.
     * @throws ReneException if the command keyword is not recognized.
     */
    public ParsedCommand parse(String input) throws ReneException {
        CommandType commandType = CommandType.fromInput(input);
        if (commandType == null) {
            throw new ReneException(UNKNOWN_COMMAND_MESSAGE);
        }

        String argument = input.substring(commandType.getKeyword().length()).trim();
        return new ParsedCommand(commandType, argument);
    }

    /**
     * Creates a task from a parsed task-creation command.
     *
     * @param command a todo, deadline, or event command.
     * @return the task described by the command.
     * @throws ReneException if a required task detail is absent or invalid.
     */
    public Task parseTask(ParsedCommand command) throws ReneException {
        return switch (command.type()) {
            case TODO -> parseTodo(command.argument());
            case DEADLINE -> parseDeadline(command.argument());
            case EVENT -> parseEvent(command.argument());
            default -> throw new ReneException(UNKNOWN_COMMAND_MESSAGE);
        };
    }

    /**
     * Returns the one-based task number supplied to a task-list command.
     *
     * @param command a mark, unmark, or delete command.
     * @return the supplied task number.
     * @throws ReneException if the argument is not a whole number.
     */
    public int parseTaskNumber(ParsedCommand command) throws ReneException {
        try {
            return Integer.parseInt(command.argument());
        } catch (NumberFormatException exception) {
            throw new ReneException("Please give me a whole-number task position, like: "
                    + command.type().getKeyword() + " 1");
        }
    }

    /**
     * Creates a todo after validating its description.
     */
    private Task parseTodo(String description) throws ReneException {
        requireText(description, "A todo needs a description. Try: todo read chapter 3");
        return new Todo(description);
    }

    /**
     * Creates a deadline after validating its description and due date.
     */
    private Task parseDeadline(String details) throws ReneException {
        String byMarker = ArgumentMarker.BY.getText();
        int byIndex = details.indexOf(byMarker);
        if (byIndex < 0) {
            throw new ReneException("A deadline needs /by. Try: deadline submit report /by 2026-08-31");
        }

        String description = details.substring(0, byIndex).trim();
        String byText = details.substring(byIndex + byMarker.length()).trim();
        requireText(description, "A deadline needs a description before /by.");
        requireText(byText, "A deadline needs a due date after /by.");
        try {
            return new Deadline(description, LocalDate.parse(byText));
        } catch (DateTimeParseException exception) {
            throw new ReneException("A deadline needs a valid date in yyyy-MM-dd format. "
                    + "Try: deadline submit report /by 2026-08-31");
        }
    }

    /**
     * Creates an event after validating its description, start, and end values.
     */
    private Task parseEvent(String details) throws ReneException {
        String fromMarker = ArgumentMarker.FROM.getText();
        String toMarker = ArgumentMarker.TO.getText();
        int fromIndex = details.indexOf(fromMarker);
        int toIndex = details.indexOf(toMarker);
        if (fromIndex < 0 || toIndex < 0 || toIndex < fromIndex) {
            throw new ReneException("An event needs /from and /to. Try: event study group /from 2pm /to 4pm");
        }

        String description = details.substring(0, fromIndex).trim();
        String from = details.substring(fromIndex + fromMarker.length(), toIndex).trim();
        String to = details.substring(toIndex + toMarker.length()).trim();
        requireText(description, "An event needs a description before /from.");
        requireText(from, "An event needs a start time after /from.");
        requireText(to, "An event needs an end time after /to.");
        return new Event(description, from, to);
    }

    /**
     * Rejects a blank required command field.
     */
    private void requireText(String text, String message) throws ReneException {
        if (text.isEmpty()) {
            throw new ReneException(message);
        }
    }
}
