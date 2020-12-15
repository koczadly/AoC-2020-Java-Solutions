package uk.oczadly.karl.aoc20.solution.day15;

import uk.oczadly.karl.aoc20.PuzzleSolution;
import uk.oczadly.karl.aoc20.input.PuzzleInput;
import uk.oczadly.karl.aoc20.util.InputUtil;

import java.util.Arrays;
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
        // Array of indexes, 0 = unseen. Using int array over Map<Integer, Integer> for performance reasons.
        int[] lastIndexes = new int[iterations];
        int num = 0; // Stores the previous number
        
        for (int i = 1; i <= iterations; i++) {
            if (i <= input.size()) {
                // Use starting number from input
                num = input.get(i - 1);
                lastIndexes[num] = i;
            } else {
                // Use previous number
                int lastSpokenIndex = lastIndexes[num]; // Get previous index of previous num
                lastIndexes[num] = i - 1; // Store previous num index
                num = lastSpokenIndex == 0 ? 0 : (i - 1 - lastSpokenIndex); // Calculate diff from prev index
            }
        }
        return num;
    }

}
