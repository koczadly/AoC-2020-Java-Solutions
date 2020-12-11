package uk.oczadly.karl.aoc20.solution.day3;

import uk.oczadly.karl.aoc20.input.InputData;
import uk.oczadly.karl.aoc20.PuzzleSolution;

import java.util.List;
import java.util.stream.Stream;

/**
 * @author Karl Oczadly
 */
public class Day3Part2 extends PuzzleSolution {
    
    public Day3Part2() {
        super(3, 2); // Initializes the day and part number
    }
    
    @Override
    public Object solve(InputData inputData) {
        MapGrid map = MapGrid.load(inputData.asList());
        
        Stream<TraversalStrategy> strategies = Stream.of(
                new TraversalStrategy(1, 1),
                new TraversalStrategy(3, 1),
                new TraversalStrategy(5, 1),
                new TraversalStrategy(7, 1),
                new TraversalStrategy(1, 2));
        
        return strategies
                .mapToLong(map::traverse)
                .reduce(1, (x, y) -> x * y);
    }
    
    
    static class MapGrid {
        boolean[][] pixels;
        int width, height;
    
        MapGrid(boolean[][] pixels, int width, int height) {
            this.pixels = pixels;
            this.width = width;
            this.height = height;
        }
        
        public boolean isTree(int x, int y) {
            if (y >= height) throw new IllegalArgumentException("Y position out of range.");
            return pixels[y][x % width];
        }
        
        /** Traverses the map with the supplied strategy, and returns the number of trees hit. */
        public int traverse(TraversalStrategy strategy) {
            int collisions = 0, x = 0, y = 0;
            do {
                if (isTree(x, y))
                    collisions++;
                x += strategy.deltaX;
                y += strategy.deltaY;
            } while (y < this.height);
            return collisions;
        }
    
        /** Parses a MapGrid object from a list of input data. */
        public static MapGrid load(List<String> input) {
            int height = input.size(), width = input.get(0).length();
            boolean[][] pixels = new boolean[height][width];
            
            for (int row = 0; row < input.size(); row++) {
                char[] chars = input.get(row).toCharArray();
                for (int col = 0; col < chars.length; col++) {
                    pixels[row][col] = parseMapPixel(chars[col]);
                }
            }
            return new MapGrid(pixels, width, height);
        }
    
        /** Returns true if a tree, and false if not an empty space. */
        private static boolean parseMapPixel(char c) {
            switch (c) {
                case '.': return false;
                case '#': return true;
                default: throw new IllegalArgumentException("Unknown map pixel type.");
            }
        }
    }
    
    static class TraversalStrategy {
        int deltaX, deltaY;
    
        public TraversalStrategy(int deltaX, int deltaY) {
            this.deltaX = deltaX;
            this.deltaY = deltaY;
        }
    }
    
}
