import java.util.Scanner;

/**
 * Starts the Rene chatbot application.
 */
public class Rene {
    private static final String DIVIDER = "____________________________________________________________";

    /**
     * Prints a greeting, echoes commands, and exits when the user enters {@code bye}.
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

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String command = scanner.nextLine();
            System.out.println(DIVIDER);

            if (command.equals("bye")) {
                System.out.println("Bye. Hope to see you again soon!");
                System.out.println(DIVIDER);
                break;
            }

            System.out.println(" " + command);
            System.out.println(DIVIDER);
        }
    }
}
