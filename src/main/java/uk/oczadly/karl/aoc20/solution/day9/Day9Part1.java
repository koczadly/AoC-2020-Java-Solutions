package uk.oczadly.karl.aoc20.solution.day9;

import uk.oczadly.karl.aoc20.input.InputData;
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
    public Object solve(InputData inputData) {
        List<Long> input = inputData.asList(Long::parseLong);
        
        for (int i = 25; i < input.size(); i++) {
            long num = input.get(i);
            boolean isValid = false;
            validCheck: for (int j = i - 25; j < i; j++) {
                for (int k = i - 25; k < i; k++) {
                    if (j != k && input.get(j) + input.get(k) == num) {
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
