package uk.oczadly.karl.aoc20.solution.day11;

import uk.oczadly.karl.aoc20.input.PuzzleInput;
import uk.oczadly.karl.aoc20.PuzzleSolution;
import uk.oczadly.karl.aoc20.util.EnumIndex;
import uk.oczadly.karl.aoc20.util.Grid2D;

/**
 * @author Karl Oczadly
 */
public class Day11Part2 extends PuzzleSolution {
    
    public Day11Part2() {
        super(11, 2); // Initializes the day and part number
    }
    
    @Override
    public Object solve(PuzzleInput input) {
        // Load grid
        SeatGrid seatGrid = new SeatGrid(input.asCharGrid(SeatState.INDEX_CHAR::valueOf));
    
        // Run iterations until complete
        while (seatGrid.nextIteration());
    
        // Count occupied seats
        return seatGrid.grid.streamElements()
                .filter(s -> s == SeatState.SEAT_OCCUPIED)
                .count();
    }
    
    
    static class SeatGrid {
        private Grid2D<SeatState> grid, nextGrid;
        
        public SeatGrid(Grid2D<SeatState> grid) {
            this.grid = grid;
            this.nextGrid = new Grid2D<>(grid.getWidth(), grid.getHeight());
        }
        
        
        public SeatState getSeat(int x, int y) {
            return grid.getOutOfBounds(x, y, SeatState.FLOOR);
        }
        
        public boolean nextIteration() {
            // Process mutations
            boolean hasChanged = false;
            for (int x = 0; x < grid.getWidth(); x++) {
                for (int y = 0; y < grid.getHeight(); y++) {
                    SeatState nextState = nextIteration(x, y);
                    nextGrid.set(x, y, nextState);
                    if (grid.get(x, y) != nextState)
                        hasChanged = true;
                }
            }
            // Apply changes
            Grid2D<SeatState> oldGrid = grid; // Reuse existing grid object
            grid = nextGrid;
            nextGrid = oldGrid;
            return hasChanged;
        }
        
        private SeatState nextIteration(int x, int y) {
            SeatState state = getSeat(x, y);
            if (state != SeatState.FLOOR) {
                // Count neighbours
                int neighbours = 0;
                for (int dx = -1; dx <= 1; dx++)
                    for (int dy = -1; dy <= 1; dy++)
                        if (checkNeighbour(x, y, dx, dy)) neighbours++;
                // Mutate
                if (state == SeatState.SEAT_EMPTY && neighbours == 0) {
                    return SeatState.SEAT_OCCUPIED;
                } else if (state == SeatState.SEAT_OCCUPIED && neighbours >= 5) {
                    return SeatState.SEAT_EMPTY;
                }
            }
            return state;
        }
    
        private boolean checkNeighbour(int xPos, int yPos, int deltaX, int deltaY) {
            if (deltaX == 0 && deltaY == 0) return false;
            int x = xPos, y = yPos;
            while (x >= 0 && x < grid.getWidth() && y >= 0 && y < grid.getHeight()) {
                x += deltaX;
                y += deltaY;
                SeatState state = getSeat(x, y);
                if (state != SeatState.FLOOR)
                    return state == SeatState.SEAT_OCCUPIED;
            }
            return false;
        }
    }
    
    enum SeatState {
        FLOOR         ('.'),
        SEAT_EMPTY    ('L'),
        SEAT_OCCUPIED ('#');
        
        public static final EnumIndex<SeatState, Character> INDEX_CHAR =
                new EnumIndex<>(SeatState.class, e -> e.character);
        
        final char character;
        SeatState(char character) {
            this.character = character;
        }
    }
    
}
