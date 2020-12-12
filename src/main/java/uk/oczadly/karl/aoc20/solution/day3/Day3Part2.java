package uk.oczadly.karl.aoc20.solution.day3;

import uk.oczadly.karl.aoc20.input.InputData;
import uk.oczadly.karl.aoc20.PuzzleSolution;
import uk.oczadly.karl.aoc20.util.Grid2D;

import java.util.stream.Stream;

/**
 * @author Karl Oczadly
 */
public class Day3Part2 extends PuzzleSolution {
    
    public Day3Part2() {
        super(3, 2); // Initializes the day and part number
    }
    
    @Override
    public Object solve(InputData input) {
        Grid2D<Boolean> grid = Grid2D.fromLines(input.asList(), c -> c == '#'); // Tree = true
        
        Stream<TraversalStrategy> strategies = Stream.of(
                new TraversalStrategy(1, 1),
                new TraversalStrategy(3, 1),
                new TraversalStrategy(5, 1),
                new TraversalStrategy(7, 1),
                new TraversalStrategy(1, 2));
        
        return strategies
                .mapToLong(s -> s.traverse(grid))
                .reduce(1, (x, y) -> x * y);
    }
    
    static class TraversalStrategy {
        final int deltaX, deltaY;
        public TraversalStrategy(int deltaX, int deltaY) {
            this.deltaX = deltaX;
            this.deltaY = deltaY;
        }
        
        /** Traverses the map with the strategy, and returns the number of trees hit. */
        public int traverse(Grid2D<Boolean> grid) {
            int collisions = 0, x = 0, y = 0;
            do {
                if (grid.get(x % grid.getWidth(), y))
                    collisions++;
                x += deltaX;
                y += deltaY;
            } while (y < grid.getHeight());
            return collisions;
        }
    }
    
}
