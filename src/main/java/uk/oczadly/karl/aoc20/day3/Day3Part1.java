package uk.oczadly.karl.aoc20.day3;

import uk.oczadly.karl.aoc20.Helper;

import java.util.List;

/**
 * @author Karl Oczadly
 */
public class Day3Part1 {
    
    public static void main(String[] args) throws Exception {
        Map map = Map.load(Helper.loadInput("3"));
        
        int collisions = 0, x = 0, y = 0;
        do {
            if (map.isTree(x, y))
                collisions++;
            x += 3;
            y += 1;
        } while (y < map.height);
        System.out.printf("Tree collisions: %d%n", collisions);
    }
    
    
    static class Map {
        boolean[][] pixels;
        int width, height;
    
        Map(boolean[][] pixels, int width, int height) {
            this.pixels = pixels;
            this.width = width;
            this.height = height;
        }
        
        public boolean isTree(int x, int y) {
            if (y >= height) throw new IllegalArgumentException("Y position out of range.");
            return pixels[y][x % width];
        }
        
        public static Map load(List<String> input) {
            int height = input.size(), width = input.get(0).length();
            boolean[][] pixels = new boolean[height][width];
            
            for (int row = 0; row < input.size(); row++) {
                char[] chars = input.get(row).toCharArray();
                for (int col = 0; col < chars.length; col++) {
                    pixels[row][col] = parseMapPixel(chars[col]);
                }
            }
            return new Map(pixels, width, height);
        }
        
        private static boolean parseMapPixel(char c) {
            switch (c) {
                case '.': return false;
                case '#': return true;
                default: throw new IllegalArgumentException("Unknown map pixel type.");
            }
        }
    }
    
}
