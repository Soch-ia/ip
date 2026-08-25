import java.nio.file.Path;

/**
 * Starts and coordinates the Rene chatbot application.
 */
public class Rene {
    private static final Path DEFAULT_DATA_FILE = Path.of("data", "rene.txt");

    private final Parser parser;
    private final Storage storage;
    private final TaskList tasks;
    private final Ui ui;
    private final String loadingError;

    /**
     * Creates a Rene application backed by the specified task data file.
     *
     * @param dataFile the path of the task data file
     */
    public Rene(Path dataFile) {
        parser = new Parser();
        storage = new Storage(dataFile);
        ui = new Ui();

        TaskList loadedTasks;
        String encounteredLoadingError = null;
        try {
            loadedTasks = new TaskList(storage.loadTasks());
        } catch (ReneException exception) {
            encounteredLoadingError = exception.getMessage();
            loadedTasks = new TaskList();
        }
        tasks = loadedTasks;
        loadingError = encounteredLoadingError;
    }

    /**
     * Processes commands until the user exits or standard input ends.
     */
    public void run() {
        ui.showWelcome(loadingError);
        while (ui.hasNextCommand()) {
            String input = ui.readCommand();
            ui.showDivider();
            try {
                ParsedCommand command = parser.parse(input);
                if (command.type() == CommandType.BYE && command.argument().isEmpty()) {
                    ui.showGoodbye();
                    ui.showDivider();
                    break;
                }
                execute(command);
            } catch (ReneException exception) {
                ui.showError(exception.getMessage());
            }
            ui.showDivider();
        }
    }

    /**
     * Starts Rene using the default data file or an optional override.
     *
     * @param args an optional first argument that overrides the task data file path
     */
    public static void main(String[] args) {
        Path dataFile = args.length == 0 ? DEFAULT_DATA_FILE : Path.of(args[0]);
        new Rene(dataFile).run();
    }

    /**
     * Applies a parsed command to the task list.
     */
    private void execute(ParsedCommand command) throws ReneException {
        switch (command.type()) {
        case LIST -> ui.showTasks(tasks.getTasks());
        case MARK -> markTask(command);
        case UNMARK -> unmarkTask(command);
        case DELETE -> deleteTask(command);
        case TODO, DEADLINE, EVENT -> addTask(parser.parseTask(command));
        case BYE -> throw new ReneException(
                "I don't know that command yet. Try todo, deadline, event, list, mark, unmark, delete, or bye.");
        }
    }

    /**
     * Adds and persists a task before displaying confirmation.
     */
    private void addTask(Task task) throws ReneException {
        tasks.add(task);
        saveTasks();
        ui.showTaskAdded(task, tasks.size());
    }

    /**
     * Marks and persists a task before displaying confirmation.
     */
    private void markTask(ParsedCommand command) throws ReneException {
        Task task = tasks.mark(parser.parseTaskNumber(command));
        saveTasks();
        ui.showTaskMarked(task);
    }

    /**
     * Unmarks and persists a task before displaying confirmation.
     */
    private void unmarkTask(ParsedCommand command) throws ReneException {
        Task task = tasks.unmark(parser.parseTaskNumber(command));
        saveTasks();
        ui.showTaskUnmarked(task);
    }

    /**
     * Deletes and persists a task before displaying confirmation.
     */
    private void deleteTask(ParsedCommand command) throws ReneException {
        Task task = tasks.remove(parser.parseTaskNumber(command));
        saveTasks();
        ui.showTaskDeleted(task, tasks.size(), !tasks.isEmpty());
    }

    /**
     * Saves a snapshot of the current task list.
     */
    private void saveTasks() throws ReneException {
        storage.saveTasks(tasks.getTasks());
    }
}
