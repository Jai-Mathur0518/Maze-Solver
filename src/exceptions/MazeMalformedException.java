package exceptions;

/**
 * An exception class representing a malformed maze format.
 */
public class MazeMalformedException extends Exception {

    /**
     * Constructs a new {@code MazeMalformedException} with no detailed message.
     */
    public MazeMalformedException() {
        super();
    }

    /**
     * Constructs a new {@code MazeMalformedException} with the specified detail message.
     *
     * @param message The detail message explaining the reason for the exception.
     */
    public MazeMalformedException(String message) {
        super(message);
    }
}
