/**
 * Represents an error caused by a command that Rene cannot process.
 */
public class ReneException extends Exception {
    /**
     * Creates an exception with a user-friendly explanation.
     *
     * @param message the explanation to show to the user
     */
    public ReneException(String message) {
        super(message);
    }
}
