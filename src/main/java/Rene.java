import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Starts the Rene chatbot application.
 */
public class Rene {
    private static final String DIVIDER = "____________________________________________________________";

    /**
     * Prints a greeting, stores task descriptions, and exits when the user enters {@code bye}.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        String banner = " ____\n"
                + "|  _ \\ ___ _ __   ___\n"
                + "| |_) / _ \\ '_ \\ / _ \\\n"
                + "|  _ <  __/ | | |  __/\n"
                + "|_| \\_\\___|_| |_|\\___|";

        System.out.println(DIVIDER);
        System.out.println(banner);
        System.out.println("Hello! I'm Rene.");
        System.out.println("What can I do for you?");
        System.out.println(DIVIDER);

        List<Task> tasks = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String command = scanner.nextLine();
            System.out.println(DIVIDER);

            CommandType commandType = CommandType.fromInput(command);
            if (command.equals(CommandType.BYE.getKeyword())) {
                System.out.println("Bye. Hope to see you again soon!");
                System.out.println(DIVIDER);
                break;
            }

            try {
                if (commandType == null) {
                    throw new ReneException("I don't know that command yet. Try todo, deadline, event, list, mark, unmark, delete, or bye.");
                }
                switch (commandType) {
                case LIST -> printTasks(tasks);
                case MARK -> markTask(command, tasks);
                case UNMARK -> unmarkTask(command, tasks);
                case DELETE -> deleteTask(command, tasks);
                case TODO -> addTodo(command, tasks);
                case DEADLINE -> addDeadline(command, tasks);
                case EVENT -> addEvent(command, tasks);
                case BYE -> throw new ReneException(
                        "I don't know that command yet. Try todo, deadline, event, list, mark, unmark, delete, or bye.");
                }
            } catch (ReneException exception) {
                System.out.println(" Oops — " + exception.getMessage());
            }
            System.out.println(DIVIDER);
        }
    }

    /**
     * Creates a todo after validating its description.
     */
    private static void addTodo(String command, List<Task> tasks) throws ReneException {
        String description = command.substring(CommandType.TODO.getKeyword().length()).trim();
        requireText(description, "A todo needs a description. Try: todo read chapter 3");
        addTask(new Todo(description), tasks);
    }

    /**
     * Creates a deadline after validating its description and due date.
     */
    private static void addDeadline(String command, List<Task> tasks) throws ReneException {
        String details = command.substring(CommandType.DEADLINE.getKeyword().length()).trim();
        String byMarker = ArgumentMarker.BY.getText();
        int byIndex = details.indexOf(byMarker);
        if (byIndex < 0) {
            throw new ReneException("A deadline needs /by. Try: deadline submit report /by Friday");
        }
        String description = details.substring(0, byIndex).trim();
        String by = details.substring(byIndex + byMarker.length()).trim();
        requireText(description, "A deadline needs a description before /by.");
        requireText(by, "A deadline needs a due date after /by.");
        addTask(new Deadline(description, by), tasks);
    }

    /**
     * Creates an event after validating its description, start, and end values.
     */
    private static void addEvent(String command, List<Task> tasks) throws ReneException {
        String details = command.substring(CommandType.EVENT.getKeyword().length()).trim();
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
        addTask(new Event(description, from, to), tasks);
    }

    /**
     * Rejects blank required command fields.
     */
    private static void requireText(String text, String message) throws ReneException {
        if (text.isEmpty()) {
            throw new ReneException(message);
        }
    }

    /**
     * Stores a task and prints confirmation together with the new task count.
     *
     * @param task the task to add
     * @param tasks the dynamic list that stores all task types as {@link Task} objects
     */
    private static void addTask(Task task, List<Task> tasks) {
        tasks.add(task);
        System.out.println(" Got it. I've added this task:");
        System.out.println("   " + task);
        printTaskCount(tasks.size());
    }

    /**
     * Prints every stored task with a number starting at one.
     *
     * @param tasks the dynamic list that holds task descriptions
     */
    private static void printTasks(List<Task> tasks) {
        System.out.println(" Here are the tasks in your list:");
        for (int index = 0; index < tasks.size(); index++) {
            System.out.println(" " + (index + 1) + "." + tasks.get(index));
        }
    }

    /**
     * Prints the current task count with grammatically correct singular or plural wording.
     *
     * @param taskCount the number of tasks currently stored
     */
    private static void printTaskCount(int taskCount) {
        String taskWord = taskCount == 1 ? "task" : "tasks";
        System.out.println(" Now you have " + taskCount + " " + taskWord + " in the list.");
    }

    /**
     * Marks the task identified by a one-based task number as completed.
     *
     * @param command the full {@code mark} command entered by the user
     * @param tasks the dynamic list that holds task descriptions and their completion status
     */
    private static void markTask(String command, List<Task> tasks) throws ReneException {
        try {
            int taskNumber = Integer.parseInt(command.substring(CommandType.MARK.getKeyword().length()).trim());
            int taskIndex = taskNumber - 1;
            if (taskIndex < 0 || taskIndex >= tasks.size()) {
                throw new ReneException("That task number is not in the list yet.");
            }
            if (tasks.get(taskIndex).isDone()) {
                throw new ReneException("That task is already done — no need to mark it twice.");
            }

            tasks.get(taskIndex).markAsDone();
            System.out.println(" Nice! I've marked this task as done:");
            System.out.println("   " + tasks.get(taskIndex));
        } catch (NumberFormatException exception) {
            throw new ReneException("Please give me a whole-number task position, like: mark 1");
        }
    }

    /**
     * Marks the task identified by a one-based task number as incomplete.
     *
     * @param command the full {@code unmark} command entered by the user
     * @param tasks the dynamic list that holds task descriptions and their completion status
     */
    private static void unmarkTask(String command, List<Task> tasks) throws ReneException {
        try {
            int taskNumber = Integer.parseInt(command.substring(CommandType.UNMARK.getKeyword().length()).trim());
            int taskIndex = taskNumber - 1;
            if (taskIndex < 0 || taskIndex >= tasks.size()) {
                throw new ReneException("That task number is not in the list yet.");
            }
            if (!tasks.get(taskIndex).isDone()) {
                throw new ReneException("That task is not done yet, so there is nothing to unmark.");
            }

            tasks.get(taskIndex).unmarkAsDone();
            System.out.println(" OK, I've marked this task as not done yet:");
            System.out.println("   " + tasks.get(taskIndex));
        } catch (NumberFormatException exception) {
            throw new ReneException("Please give me a whole-number task position, like: unmark 1");
        }
    }

    /**
     * Removes the task identified by a one-based task number.
     *
     * @param command the full {@code delete} command entered by the user
     * @param tasks the dynamic list that holds all tasks
     */
    private static void deleteTask(String command, List<Task> tasks) throws ReneException {
        try {
            int taskNumber = Integer.parseInt(command.substring(CommandType.DELETE.getKeyword().length()).trim());
            int taskIndex = taskNumber - 1;
            if (taskIndex < 0 || taskIndex >= tasks.size()) {
                throw new ReneException("That task number is not in the list yet.");
            }

            Task removedTask = tasks.remove(taskIndex);
            System.out.println(" Noted. I've removed this task:");
            System.out.println("   " + removedTask);
            printTaskCount(tasks.size());
        } catch (NumberFormatException exception) {
            throw new ReneException("Please give me a whole-number task position, like: delete 1");
        }
    }
}
