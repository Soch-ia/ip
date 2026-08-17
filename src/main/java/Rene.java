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

        String[] tasks = new String[MAX_TASKS];
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
                printTasks(tasks, taskCount);
            } else {
                tasks[taskCount] = command;
                taskCount++;
                System.out.println(" added: " + command);
            }
            System.out.println(DIVIDER);
        }
    }

    /**
     * Prints every stored task with a number starting at one.
     *
     * @param tasks the array that holds task descriptions
     * @param taskCount the number of task descriptions currently stored
     */
    private static void printTasks(String[] tasks, int taskCount) {
        for (int index = 0; index < taskCount; index++) {
            System.out.println(" " + (index + 1) + ". " + tasks[index]);
        }
    }
}
