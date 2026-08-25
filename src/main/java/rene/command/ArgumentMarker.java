package rene.command;

/**
 * Represents fixed markers that separate fields in task-creation commands.
 */
public enum ArgumentMarker {
    BY(" /by "),
    FROM(" /from "),
    TO(" /to ");

    private final String text;

    /**
     * Creates a command-field marker.
     *
     * @param text the exact text used to separate the command fields
     */
    ArgumentMarker(String text) {
        this.text = text;
    }

    /**
     * Returns the text of this command-field marker.
     *
     * @return the marker text
     */
    public String getText() {
        return text;
    }
}
