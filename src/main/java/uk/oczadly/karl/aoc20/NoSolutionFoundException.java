package uk.oczadly.karl.aoc20;

/**
 * Thrown when a solution could not be found for a given set of input data.
 */
public class NoSolutionFoundException extends RuntimeException {
    
    public NoSolutionFoundException() {
        this("No valid solution was found.");
    }
    
    public NoSolutionFoundException(String message) {
        super(message);
    }
    
}
