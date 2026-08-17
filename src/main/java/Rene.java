import java.util.Scanner;

/**
 * Starts the Rene chatbot application.
 */
public class Rene {
    private static final String DIVIDER = "____________________________________________________________";
    private static final int MAX_TASKS = 100;

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

        Task[] tasks = new Task[MAX_TASKS];
        int taskCount = 0;
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String command = scanner.nextLine();
            System.out.println(DIVIDER);

            if (command.equals("bye")) {
                System.out.println("Bye. Hope to see you again soon!");
                System.out.println(DIVIDER);
                break;
            }

            if (command.equals("list")) {
                printTasks(tasks, taskCount);dea
            } else if (command.startsWith("mark ")) {
                markTask(command, tasks, taskCount);
            } else if (command.startsWith("unmark ")) {
                unmarkTask(command, tasks, taskCount);
            } else if (command.startsWith("todo ")) {
                String description = command.substring("todo ".length());
                taskCount = addTask(new Todo(description), tasks, taskCount);
            } else if (command.startsWith("deadline ")) {
                int byIndex = command.indexOf(" /by ");
                String description = command.substring("deadline ".length(), byIndex);
                String by = command.substring(byIndex + " /by ".length());
                taskCount = addTask(new Deadline(description, by), tasks, taskCount);
            } else if (command.startsWith("event ")) {
                int fromIndex = command.indexOf(" /from ");
                int toIndex = command.indexOf(" /to ");
                String description = command.substring("event ".length(), fromIndex);
                String from = command.substring(fromIndex + " /from ".length(), toIndex);
                String to = command.substring(toIndex + " /to ".length());
                taskCount = addTask(new Event(description, from, to), tasks, taskCount);
            } else {
                taskCount = addTask(new Todo(command), tasks, taskCount);
            }
            System.out.println(DIVIDER);
        }
    }

    /**
     * Stores a task and prints confirmation together with the new task count.
     *
     * @param task the task to add
     * @param tasks the array that stores all task types as {@link Task} objects
     * @param taskCount the number of tasks currently stored
     * @return the updated number of stored tasks
     */
    private static int addTask(Task task, Task[] tasks, int taskCount) {
        tasks[taskCount] = task;
        int updatedTaskCount = taskCount + 1;
        System.out.println(" Got it. I've added this task:");
        System.out.println("   " + task);
        System.out.println(" Now you have " + updatedTaskCount + " tasks in the list.");
        return updatedTaskCount;
    }

    /**
     * Prints every stored task with a number starting at one.
     *
     * @param tasks the array that holds task descriptions
     * @param taskCount the number of task descriptions currently stored
     */
    private static void printTasks(Task[] tasks, int taskCount) {
        System.out.println(" Here are the tasks in your list:");
        for (int index = 0; index < taskCount; index++) {
            System.out.println(" " + (index + 1) + "." + tasks[index]);
        }
    }

    /**
     * Marks the task identified by a one-based task number as completed.
     *
     * @param command the full {@code mark} command entered by the user
     * @param tasks the array that holds task descriptions and their completion status
     * @param taskCount the number of tasks currently stored
     */
    private static void markTask(String command, Task[] tasks, int taskCount) {
        try {
            int taskNumber = Integer.parseInt(command.substring("mark ".length()));
            int taskIndex = taskNumber - 1;
            if (taskIndex < 0 || taskIndex >= taskCount) {
                System.out.println(" Please enter a task number from the list.");
                return;
            }

            tasks[taskIndex].markAsDone();
            System.out.println(" Nice! I've marked this task as done:");
            System.out.println("   " + tasks[taskIndex]);
        } catch (NumberFormatException exception) {
            System.out.println(" Please enter a valid task number.");
        }
    }

    /**
     * Marks the task identified by a one-based task number as incomplete.
     *
     * @param command the full {@code unmark} command entered by the user
     * @param tasks the array that holds task descriptions and their completion status
     * @param taskCount the number of tasks currently stored
     */
    private static void unmarkTask(String command, Task[] tasks, int taskCount) {
        try {
            int taskNumber = Integer.parseInt(command.substring("unmark ".length()));
            int taskIndex = taskNumber - 1;
            if (taskIndex < 0 || taskIndex >= taskCount) {
                System.out.println(" Please enter a task number from the list.");
                return;
            }

            tasks[taskIndex].unmarkAsDone();
            System.out.println(" OK, I've marked this task as not done yet:");
            System.out.println("   " + tasks[taskIndex]);
        } catch (NumberFormatException exception) {
            System.out.println(" Please enter a valid task number.");
        }
    }
}
