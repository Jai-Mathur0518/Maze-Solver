package exceptions;

/**
 * An exception class representing a mismatch in maze size.
 */
public class MazeSizeMissmatchException extends Exception {

    /**
     * Constructs a new {@code MazeSizeMissmatchException} with no detailed message.
     */
    public MazeSizeMissmatchException() {
        super();
    }

    /**
     * Constructs a new {@code MazeSizeMissmatchException} with the specified detail message.
     *
     * @param message The detail message explaining the reason for the exception.
     */
    public MazeSizeMissmatchException(String message) {
        super(message);
    }
}
