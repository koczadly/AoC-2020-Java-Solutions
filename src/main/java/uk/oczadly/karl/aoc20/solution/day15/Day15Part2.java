package uk.oczadly.karl.aoc20.solution.day15;

import uk.oczadly.karl.aoc20.PuzzleSolution;
import uk.oczadly.karl.aoc20.input.PuzzleInput;
import uk.oczadly.karl.aoc20.util.InputUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Karl Oczadly
 */
public class Day15Part2 extends PuzzleSolution {
    
    public Day15Part2() { super(15, 2); } // Initializes the day and part number
    
    @Override
    public Object solve(PuzzleInput input) {
        // Literally nothing's changed from part 1 other than the iterations count... huh
        // Just gonna completely re-use the solver class from Day15Part1, you can find the source there.
        
        return new Day15Part1.Solver(
                InputUtil.split(input.asString(), ",", Integer::parseInt)) // Load input as list of ints
                .solve(30000000); // Solve
    }

}
