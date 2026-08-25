package rene.ui;

import java.util.List;
import java.util.Scanner;

import rene.task.Task;

/**
 * Handles all console input and output for Rene.
 */
public class Ui {
    private static final String DIVIDER = "____________________________________________________________";
    private static final String BANNER = " ____\n"
            + "|  _ \\ ___ _ __   ___\n"
            + "| |_) / _ \\ '_ \\ / _ \\\n"
            + "|  _ <  __/ | | |  __/\n"
            + "|_| \\_\\___|_| |_|\\___|";

    private final Scanner scanner;

    /**
     * Creates a console UI that reads from standard input.
     */
    public Ui() {
        scanner = new Scanner(System.in);
    }

    /**
     * Returns whether another command is available from the user.
     *
     * @return {@code true} if another line can be read
     */
    public boolean hasNextCommand() {
        return scanner.hasNextLine();
    }

    /**
     * Reads the next command entered by the user.
     *
     * @return the complete command line
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    /**
     * Displays Rene's greeting and any problem encountered while loading tasks.
     *
     * @param loadingError the loading error to display, or {@code null} when loading succeeded
     */
    public void showWelcome(String loadingError) {
        showDivider();
        System.out.println(BANNER);
        System.out.println("Hello! I'm Rene.");
        System.out.println("What can I do for you?");
        showDivider();
        if (loadingError != null) {
            showError(loadingError);
            showDivider();
        }
    }

    /**
     * Displays the farewell message.
     */
    public void showGoodbye() {
        System.out.println("Bye. Hope to see you again soon!");
    }

    /**
     * Displays a user-friendly command or storage error.
     *
     * @param message the explanation to display
     */
    public void showError(String message) {
        System.out.println(" Oops — " + message);
    }

    /**
     * Displays confirmation that a task was added.
     *
     * @param task the added task
     * @param taskCount the resulting task count
     */
    public void showTaskAdded(Task task, int taskCount) {
        System.out.println(" Got it. I've added this task:");
        System.out.println("   " + task);
        showTaskCount(taskCount);
    }

    /**
     * Displays all supplied tasks with one-based numbers.
     *
     * @param tasks the tasks to display
     */
    public void showTasks(List<Task> tasks) {
        System.out.println(" Here are the tasks in your list:");
        for (int index = 0; index < tasks.size(); index++) {
            System.out.println(" " + (index + 1) + "." + tasks.get(index));
        }
    }

    /**
     * Displays confirmation that a task was marked as completed.
     *
     * @param task the updated task
     */
    public void showTaskMarked(Task task) {
        System.out.println(" Nice! I've marked this task as done:");
        System.out.println("   " + task);
    }

    /**
     * Displays confirmation that a task was marked as incomplete.
     *
     * @param task the updated task
     */
    public void showTaskUnmarked(Task task) {
        System.out.println(" OK, I've marked this task as not done yet:");
        System.out.println("   " + task);
    }

    /**
     * Displays confirmation that a task was removed.
     *
     * @param task the removed task
     * @param taskCount the resulting task count
     * @param tasksWereRenumbered whether any tasks remain and received new display numbers
     */
    public void showTaskDeleted(Task task, int taskCount, boolean tasksWereRenumbered) {
        System.out.println(" Noted. I've removed this task:");
        System.out.println("   " + task);
        showTaskCount(taskCount);
        if (tasksWereRenumbered) {
            System.out.println(" The remaining tasks have been renumbered.");
        }
    }

    /**
     * Displays the divider used to separate console interactions.
     */
    public void showDivider() {
        System.out.println(DIVIDER);
    }

    /**
     * Displays a task count with grammatically correct wording.
     */
    private void showTaskCount(int taskCount) {
        String taskWord = taskCount == 1 ? "task" : "tasks";
        System.out.println(" Now you have " + taskCount + " " + taskWord + " in the list.");
    }
}
