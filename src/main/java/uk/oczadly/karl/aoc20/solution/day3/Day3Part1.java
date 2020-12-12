package uk.oczadly.karl.aoc20.solution.day3;

import uk.oczadly.karl.aoc20.PuzzleSolution;
import uk.oczadly.karl.aoc20.input.PuzzleInput;
import uk.oczadly.karl.aoc20.util.Grid2D;

/**
 * @author Karl Oczadly
 */
public class Day3Part1 extends PuzzleSolution {
    
    public Day3Part1() {
        super(3, 1); // Initializes the day and part number
    }
    
    @Override
    public Object solve(PuzzleInput input) {
        Grid2D<Boolean> grid = input.asGrid(c -> c == '#');  // Tree = true
        
        int collisions = 0, x = 0, y = 0;
        do {
            if (grid.get(x % grid.getWidth(), y))
                collisions++;
            x += 3;
            y += 1;
        } while (y < grid.getHeight());
        return collisions;
    }
    
}
