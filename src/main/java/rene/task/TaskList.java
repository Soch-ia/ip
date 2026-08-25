package rene.task;

import java.util.ArrayList;
import java.util.List;

import rene.exception.ReneException;

/**
 * Manages the tasks in Rene's current task list.
 */
public class TaskList {
    private final List<Task> tasks;

    /**
     * Creates an empty task list.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Creates a task list containing the supplied tasks.
     *
     * @param tasks the initial tasks
     */
    public TaskList(List<Task> tasks) {
        this.tasks = new ArrayList<>(tasks);
    }

    /**
     * Adds a task to the end of the list.
     *
     * @param task the task to add
     */
    public void add(Task task) {
        tasks.add(task);
    }

    /**
     * Marks a task as completed.
     *
     * @param taskNumber the one-based position of the task
     * @return the task whose status changed
     * @throws ReneException if the task number is invalid or the task is already done
     */
    public Task mark(int taskNumber) throws ReneException {
        Task task = getByTaskNumber(taskNumber);
        if (task.isDone()) {
            throw new ReneException("That task is already done — no need to mark it twice.");
        }

        task.markAsDone();
        return task;
    }

    /**
     * Marks a task as incomplete.
     *
     * @param taskNumber the one-based position of the task
     * @return the task whose status changed
     * @throws ReneException if the task number is invalid or the task is already incomplete
     */
    public Task unmark(int taskNumber) throws ReneException {
        Task task = getByTaskNumber(taskNumber);
        if (!task.isDone()) {
            throw new ReneException("That task is not done yet, so there is nothing to unmark.");
        }

        task.unmarkAsDone();
        return task;
    }

    /**
     * Removes a task from the list.
     *
     * @param taskNumber the one-based position of the task
     * @return the removed task
     * @throws ReneException if the task number is invalid
     */
    public Task remove(int taskNumber) throws ReneException {
        int taskIndex = getTaskIndex(taskNumber);
        return tasks.remove(taskIndex);
    }

    /**
     * Returns a snapshot of the tasks in their current order.
     *
     * @return an unmodifiable copy of the task list
     */
    public List<Task> getTasks() {
        return List.copyOf(tasks);
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return the task count
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Returns whether the list contains no tasks.
     *
     * @return {@code true} if the list is empty
     */
    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    /**
     * Returns the task at a one-based position after validating it.
     */
    private Task getByTaskNumber(int taskNumber) throws ReneException {
        return tasks.get(getTaskIndex(taskNumber));
    }

    /**
     * Converts a valid one-based task number to its zero-based list index.
     */
    private int getTaskIndex(int taskNumber) throws ReneException {
        int taskIndex = taskNumber - 1;
        if (taskIndex < 0 || taskIndex >= tasks.size()) {
            throw new ReneException("That task number is not in the list yet.");
        }
        return taskIndex;
    }
}
