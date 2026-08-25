package rene.storage;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import rene.exception.ReneException;
import rene.task.Deadline;
import rene.task.Event;
import rene.task.Task;
import rene.task.Todo;

/**
 * Loads tasks from a text file and saves task-list changes to that file.
 */
public class Storage {
    private static final String FIELD_SEPARATOR = " | ";

    private final Path filePath;

    /**
     * Creates storage that reads from and writes to the given file.
     *
     * @param filePath the path of the task data file.
     */
    public Storage(Path filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads all saved tasks, creating an empty data file first when necessary.
     *
     * @return the tasks reconstructed from the data file.
     * @throws ReneException if the file cannot be read or contains invalid data.
     */
    public List<Task> loadTasks() throws ReneException {
        try {
            ensureDataFileExists();
            List<String> lines = Files.readAllLines(filePath, StandardCharsets.UTF_8);
            List<Task> tasks = new ArrayList<>();
            for (int index = 0; index < lines.size(); index++) {
                String line = lines.get(index);
                if (!line.isBlank()) {
                    tasks.add(parseTask(line, index + 1));
                }
            }
            return tasks;
        } catch (IOException exception) {
            throw new ReneException("I couldn't load tasks from " + filePath + ".", exception);
        }
    }

    /**
     * Replaces the data file contents with the current task list.
     *
     * @param tasks the tasks to save.
     * @throws ReneException if the file cannot be written.
     */
    public void saveTasks(List<Task> tasks) throws ReneException {
        List<String> lines = new ArrayList<>();
        for (Task task : tasks) {
            lines.add(formatTask(task));
        }

        try {
            ensureDataFileExists();
            Files.write(filePath, lines, StandardCharsets.UTF_8);
        } catch (IOException exception) {
            throw new ReneException("I couldn't save tasks to " + filePath + ".", exception);
        }
    }

    /**
     * Creates the parent directory and data file when Rene is run for the first time.
     */
    private void ensureDataFileExists() throws IOException {
        Path parentDirectory = filePath.getParent();
        if (parentDirectory != null) {
            Files.createDirectories(parentDirectory);
        }
        if (Files.notExists(filePath)) {
            Files.createFile(filePath);
        }
    }

    /**
     * Converts a task to Rene's human-readable storage format.
     */
    private String formatTask(Task task) {
        String status = task.isDone() ? "1" : "0";
        String basicFields = task.getTaskType().getIcon()
                + FIELD_SEPARATOR + status
                + FIELD_SEPARATOR + task.getDescription();

        return switch (task.getTaskType()) {
            case TODO -> basicFields;
            case DEADLINE -> basicFields + FIELD_SEPARATOR + ((Deadline) task).getBy();
            case EVENT -> basicFields
                    + FIELD_SEPARATOR + ((Event) task).getFrom()
                    + FIELD_SEPARATOR + ((Event) task).getTo();
        };
    }

    /**
     * Reconstructs one task from a line in Rene's storage format.
     */
    private Task parseTask(String line, int lineNumber) throws ReneException {
        String[] fields = line.split(" \\| ", -1);
        if (fields.length < 3) {
            throw invalidData(lineNumber);
        }

        Task task = switch (fields[0]) {
            case "T" -> parseTodo(fields, lineNumber);
            case "D" -> parseDeadline(fields, lineNumber);
            case "E" -> parseEvent(fields, lineNumber);
            default -> throw invalidData(lineNumber);
        };

        if (fields[1].equals("1")) {
            task.markAsDone();
        } else if (!fields[1].equals("0")) {
            throw invalidData(lineNumber);
        }
        return task;
    }

    /**
     * Reconstructs a todo from its stored fields.
     */
    private Task parseTodo(String[] fields, int lineNumber) throws ReneException {
        requireFieldCount(fields, 3, lineNumber);
        requireNonBlank(fields[2], lineNumber);
        return new Todo(fields[2]);
    }

    /**
     * Reconstructs a deadline from its stored fields.
     */
    private Task parseDeadline(String[] fields, int lineNumber) throws ReneException {
        requireFieldCount(fields, 4, lineNumber);
        requireNonBlank(fields[2], lineNumber);
        requireNonBlank(fields[3], lineNumber);
        try {
            return new Deadline(fields[2], LocalDate.parse(fields[3]));
        } catch (DateTimeParseException exception) {
            throw invalidData(lineNumber);
        }
    }

    /**
     * Reconstructs an event from its stored fields.
     */
    private Task parseEvent(String[] fields, int lineNumber) throws ReneException {
        requireFieldCount(fields, 5, lineNumber);
        requireNonBlank(fields[2], lineNumber);
        requireNonBlank(fields[3], lineNumber);
        requireNonBlank(fields[4], lineNumber);
        return new Event(fields[2], fields[3], fields[4]);
    }

    /**
     * Rejects a stored task that has an unexpected number of fields.
     */
    private void requireFieldCount(String[] fields, int expectedCount, int lineNumber) throws ReneException {
        if (fields.length != expectedCount) {
            throw invalidData(lineNumber);
        }
    }

    /**
     * Rejects a required stored task field that is blank.
     */
    private void requireNonBlank(String field, int lineNumber) throws ReneException {
        if (field.isBlank()) {
            throw invalidData(lineNumber);
        }
    }

    /**
     * Creates a consistent error for malformed task data.
     */
    private ReneException invalidData(int lineNumber) {
        return new ReneException("I couldn't understand line " + lineNumber + " in " + filePath + ".");
    }
}
