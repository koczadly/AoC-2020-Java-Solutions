package uk.oczadly.karl.aoc20.solution.day1;

import uk.oczadly.karl.aoc20.input.InputData;
import uk.oczadly.karl.aoc20.NoSolutionFoundException;
import uk.oczadly.karl.aoc20.PuzzleSolution;

import java.util.List;

/**
 * @author Karl Oczadly
 */
public class Day1Part2 extends PuzzleSolution {
    
    public Day1Part2() {
        super(1, 2); // Initializes the day and part number
    }
    
    @Override
    public Object solve(InputData inputData) {
        List<Integer> ints = inputData.asList(Integer::valueOf);
        
        for (int i : ints) {
            for (int j : ints) {
                for (int k : ints) {
                    if (i != j && i != k && j != k && i + j + k == 2020) {
                        return i * j * k;
                    }
                }
            }
        }
        throw new NoSolutionFoundException();
    }

}
