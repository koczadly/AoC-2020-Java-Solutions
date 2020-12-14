package uk.oczadly.karl.aoc20.input;

/**
 * Thrown when a solution could not be found for a given set of input data.
 */
public class NoValidSolutionException extends IllegalInputException {
    
    public NoValidSolutionException() {
        this("No valid solution was found.");
    }
    
    public NoValidSolutionException(String message) {
        super(message);
    }
    
}
