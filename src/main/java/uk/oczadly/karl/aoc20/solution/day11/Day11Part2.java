package uk.oczadly.karl.aoc20.solution.day11;

import uk.oczadly.karl.aoc20.input.PuzzleInput;
import uk.oczadly.karl.aoc20.PuzzleSolution;
import uk.oczadly.karl.aoc20.util.EnumIndex;
import uk.oczadly.karl.aoc20.util.Grid2D;

/**
 * @author Karl Oczadly
 */
public class Day11Part2 extends PuzzleSolution {
    
    public Day11Part2() { super(11, 2); } // Initializes the day and part number
    
    @Override
    public Object solve(PuzzleInput input) {
        // Load grid
        SeatGrid seatGrid = new SeatGrid(input.asGrid(SeatState.INDEX_CHAR::valueOf));
    
        // Keep running iterations until complete (returns false after no changes have been made)
        while (seatGrid.nextIteration());
    
        // Count occupied seats
        return seatGrid.grid.streamElements()
                .filter(s -> s == SeatState.SEAT_OCCUPIED) // Filter only occupied seats
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
    
        /** Runs the next iteration for all cells, and returns true if at least one cell changed. */
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
            
            // Apply changes, and reuse the existing grid object (swap the two grids)
            Grid2D<SeatState> oldGrid = grid;
            grid = nextGrid;
            nextGrid = oldGrid;
            return hasChanged;
        }
        
        /** Runs the next iteration for the single specified cell, returning the new state. */
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
                    return SeatState.SEAT_OCCUPIED; // Mutate to occupied
                } else if (state == SeatState.SEAT_OCCUPIED && neighbours >= 5) {
                    return SeatState.SEAT_EMPTY; // Mutate to empty
                }
            }
            return state; // Cell state hasn't changed
        }
    
        /** Returns true if there is a neighbour in the specified direction. */
        private boolean checkNeighbour(int xPos, int yPos, int deltaX, int deltaY) {
            if (deltaX == 0 && deltaY == 0) return false; // Checking self, not a neighbour
            int x = xPos, y = yPos;
            while (x >= 0 && x < grid.getWidth() && y >= 0 && y < grid.getHeight()) {
                SeatState state = getSeat(x += deltaX, y += deltaY); // Increment x/y vals, and get state
                if (state != SeatState.FLOOR) // Once we find a cell that isn't the floor, return and exit...
                    return state == SeatState.SEAT_OCCUPIED; // Return true if occupied, false if not
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
