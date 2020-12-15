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
        // Literally nothing has changed from part 1 other than the iterations count... huh.
        // Just gonna completely re-use the method from Day15Part1, you can find the source there.
        
        return Day15Part1.solve(InputUtil.split(input.asString(), ",", Integer::parseInt), 30000000);
    }

}
