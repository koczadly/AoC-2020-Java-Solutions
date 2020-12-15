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
public class Day15Part1 extends PuzzleSolution {
    
    public Day15Part1() { super(15, 1); } // Initializes the day and part number
    
     @Override
    public Object solve(PuzzleInput input) {
        return solve(InputUtil.split(input.asString(), ",", Integer::parseInt), 2020);
    }
    
    
    public static int solve(List<Integer> input, int iterations) {
        Map<Integer, Integer> lastIndexes = new HashMap<>(); // Number -> last spoken index
        int num = 0; // Stores the previous number
        for (int i = 0; i < iterations; i++) {
            if (i < input.size()) {
                // Use starting number from input
                num = input.get(i);
                lastIndexes.put(num, i);
            } else {
                // Use previous number
                Integer lastSpokenIndex = lastIndexes.put(num, i - 1); // Add prev number to map and get last index
                num = lastSpokenIndex == null ? 0 : (i - 1 - lastSpokenIndex); // Calculate diff from prev index
            }
        }
        return num;
    }

}
