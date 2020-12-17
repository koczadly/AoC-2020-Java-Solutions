package uk.oczadly.karl.aoc20.solution.day17;

import uk.oczadly.karl.aoc20.PuzzleSolution;
import uk.oczadly.karl.aoc20.input.PuzzleInput;
import uk.oczadly.karl.aoc20.util.Grid2D;
import uk.oczadly.karl.aoc20.util.Grid3D;

/**
 * @author Karl Oczadly
 */
public class Day17Part1 extends PuzzleSolution {
    
    public Day17Part1() { super(17, 1); } // Initializes the day and part number
    
    @Override
    public Object solve(PuzzleInput input) {
        // Parse input plane, and convert to 3D grid
        Grid2D<Boolean> initPlane = input.asGrid(c -> c == '#');
        Grid3D<Boolean> grid = new Grid3D<>(initPlane.getWidth(), initPlane.getHeight(), 1, false);
        grid.setPlaneZ(0, 0, 0, initPlane);
        
        // Run 6 iterations
        ConwayCube cubes = new ConwayCube(grid);
        for (int i = 0; i < 6; i++)
            cubes.nextIteration();
        
        // Count active cells
        return cubes.grid.streamElements()
                .filter(Boolean::valueOf) // True = active
                .count();
    }
    
    
    static class ConwayCube {
        private Grid3D<Boolean> grid;
    
        public ConwayCube(Grid3D<Boolean> grid) {
            this.grid = grid;
        }
        
        
        /** Returns the current cell state for the given next coordinate. Each iteration increases the size of the
         *  grid by 2, acting as a padding around the input. */
        private boolean getStateFor(int x, int y, int z) {
            return grid.getOutOfBounds(x - 1, y - 1, z - 1, false);
        }
        
        public void nextIteration() {
            // Create the next grid container
            Grid3D<Boolean> nextGrid = new Grid3D<>(grid.getLengthX() + 2, grid.getLengthY() + 2,
                    grid.getLengthZ() + 2, false);
            
            // Process mutations
            for (int z = 0; z < nextGrid.getLengthZ(); z++)
                for (int x = 0; x < nextGrid.getLengthX(); x++)
                    for (int y = 0; y < nextGrid.getLengthY(); y++)
                        nextGrid.set(x, y, z, nextIteration(x, y, z));
                    
            // Apply changes
            grid = nextGrid;
        }
    
        public boolean nextIteration(int x, int y, int z) {
            boolean active = getStateFor(x, y, z); // Current state
            // Count neighbours
            int neighbours = 0;
            for (int dx = -1; dx <= 1; dx++) {
                for (int dy = -1; dy <= 1; dy++) {
                    for (int dz = -1; dz <= 1; dz++) {
                        if (dx == 0 && dy == 0 && dz == 0) continue; // Ignore self, not a neighbour
                        if (getStateFor(x + dx, y + dy, z + dz))
                            neighbours++;
                    }
                }
            }
            // Mutate
            if (active) {
                return neighbours >= 2 && neighbours <= 3;
            } else {
                return neighbours == 3;
            }
        }
    }

}
