package uk.oczadly.karl.aoc20.solution.day1;

import uk.oczadly.karl.aoc20.input.InputData;
import uk.oczadly.karl.aoc20.NoSolutionFoundException;
import uk.oczadly.karl.aoc20.PuzzleSolution;

import java.util.List;

/**
 * @author Karl Oczadly
 */
public class Day1Part1 extends PuzzleSolution {
    
    public Day1Part1() {
        super(1, 1); // Initializes the day and part number
    }
    
    @Override
    public Object solve(InputData inputData) {
        List<Integer> ints = inputData.asList(Integer::parseInt);
        
        for (int i : ints) {
            for (int j : ints) {
                if (i != j && i + j == 2020) {
                    return i * j;
                }
            }
        }
        throw new NoSolutionFoundException();
    }

}
