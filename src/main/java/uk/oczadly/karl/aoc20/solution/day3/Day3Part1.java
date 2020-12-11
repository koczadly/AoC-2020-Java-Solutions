package uk.oczadly.karl.aoc20.solution.day3;

import uk.oczadly.karl.aoc20.input.InputData;
import uk.oczadly.karl.aoc20.PuzzleSolution;

import java.util.List;

/**
 * @author Karl Oczadly
 */
public class Day3Part1 extends PuzzleSolution {
    
    public Day3Part1() {
        super(3, 1); // Initializes the day and part number
    }
    
    @Override
    public Object solve(InputData inputData) {
        MapGrid map = MapGrid.load(inputData.asList());
        
        int collisions = 0, x = 0, y = 0;
        do {
            if (map.isTree(x, y))
                collisions++;
            x += 3;
            y += 1;
        } while (y < map.height);
        return collisions;
    }
    
    
    static class MapGrid {
        boolean[][] pixels;
        int width, height;
    
        MapGrid(boolean[][] pixels, int width, int height) {
            this.pixels = pixels;
            this.width = width;
            this.height = height;
        }
        
        /** Returns true if the (x,y) position is a tree. */
        public boolean isTree(int x, int y) {
            if (y >= height) throw new IllegalArgumentException("Y position out of range.");
            return pixels[y][x % width];
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
    
}
