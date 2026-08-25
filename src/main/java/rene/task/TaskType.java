package rene.task;

/**
 * Represents the supported task categories and their display icons.
 */
public enum TaskType {
    TODO("T"),
    DEADLINE("D"),
    EVENT("E");

    private final String icon;

    /**
     * Creates a task type with its display icon.
     *
     * @param icon the letter used to display this task type
     */
    TaskType(String icon) {
        this.icon = icon;
    }

    /**
     * Returns the icon used to display this task type.
     *
     * @return the task type icon
     */
    public String getIcon() {
        return icon;
    }
}
