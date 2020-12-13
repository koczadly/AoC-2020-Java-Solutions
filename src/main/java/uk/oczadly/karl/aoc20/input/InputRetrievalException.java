package uk.oczadly.karl.aoc20.input;

/**
 * @author Karl Oczadly
 */
public class InputRetrievalException extends RuntimeException {
    
    public InputRetrievalException() {}
    
    public InputRetrievalException(String message) {
        super(message);
    }
    
    public InputRetrievalException(Throwable cause) {
        super(cause);
    }
    
}
