package uk.oczadly.karl.aoc20.solution.day9;

import uk.oczadly.karl.aoc20.input.PuzzleInput;
import uk.oczadly.karl.aoc20.NoSolutionFoundException;
import uk.oczadly.karl.aoc20.PuzzleSolution;

import java.util.List;

/**
 * @author Karl Oczadly
 */
public class Day9Part2 extends PuzzleSolution {
    
    public Day9Part2() {
        super(9, 2); // Initializes the day and part number
    }
    
    @Override
    public Object solve(PuzzleInput input) {
        List<Long> in = input.asList(Long::parseLong);
        
        // Find invalid number
        long targetNum = 0;
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
            if (!isValid) {
                targetNum = num;
                break;
            }
        }
        
        // Find set of numbers that add up to 'targetNum'
        for (int startIndex = 0; startIndex < in.size(); startIndex++) {
            long currentSum = 0, min = Long.MAX_VALUE, max = Long.MIN_VALUE;
            for (int i = startIndex; i < in.size(); i++) {
                long val = in.get(i);
                
                currentSum += val;
                if (val < min) min = val;
                if (val > max) max = val;
                
                if (i > startIndex && currentSum == targetNum) {
                    // Solution found!
                    return  min + max;
                }
            }
        }
        
        throw new NoSolutionFoundException();
    }
    
}
