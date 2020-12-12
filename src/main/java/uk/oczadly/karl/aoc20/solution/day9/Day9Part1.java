package uk.oczadly.karl.aoc20.solution.day9;

import uk.oczadly.karl.aoc20.input.PuzzleInput;
import uk.oczadly.karl.aoc20.NoSolutionFoundException;
import uk.oczadly.karl.aoc20.PuzzleSolution;

import java.util.List;

/**
 * @author Karl Oczadly
 */
public class Day9Part1 extends PuzzleSolution {
    
    public Day9Part1() {
        super(9, 1); // Initializes the day and part number
    }
    
    @Override
    public Object solve(PuzzleInput input) {
        List<Long> in = input.asList(Long::parseLong);
        
        for (int i = 25; i < in.size(); i++) {
            long num = in.get(i);
            boolean isValid = false;
            validCheck: for (int j = i - 25; j < i; j++) {
                for (int k = i - 25; k < i; k++) {
                    if (j != k && in.get(j) + in.get(k) == num) {
                        isValid = true;
                        break validCheck;
                    }
                }
            }
            if (!isValid)
                return num;
        }
        throw new NoSolutionFoundException();
    }
    
}
