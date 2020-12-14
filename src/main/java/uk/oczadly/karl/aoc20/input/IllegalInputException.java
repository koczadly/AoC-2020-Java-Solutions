package uk.oczadly.karl.aoc20.input;

/**
 * Thrown when a solution could not be found for a given set of input data.
 */
public class IllegalInputException extends IllegalArgumentException {
    
    public IllegalInputException() {
        this("Input did not match required or expected format.");
    }
    
    public IllegalInputException(String message) {
        super(message);
    }
    
}
