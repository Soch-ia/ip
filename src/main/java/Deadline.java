/**
 * Represents a task that must be completed by a specified date or time.
 */
public class Deadline extends Task {
    private final String by;

    /**
     * Creates an incomplete deadline task.
     *
     * @param description the text describing the task
     * @param by the date or time by which the task must be completed
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    @Override
    protected TaskType getTaskType() {
        return TaskType.DEADLINE;
    }

    /**
     * Returns the deadline in the chatbot's display format.
     *
     * @return the basic task details followed by the deadline
     */
    @Override
    public String toString() {
        return super.toString() + " (by: " + by + ")";
    }
}
