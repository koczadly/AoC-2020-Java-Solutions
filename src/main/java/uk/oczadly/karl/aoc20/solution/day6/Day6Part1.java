package uk.oczadly.karl.aoc20.solution.day6;

import uk.oczadly.karl.aoc20.input.InputData;
import uk.oczadly.karl.aoc20.PuzzleSolution;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Karl Oczadly
 */
public class Day6Part1 extends PuzzleSolution {
    
    public Day6Part1() {
        super(6, 1); // Initializes the day and part number
    }
    
    @Override
    public Object solve(InputData input) {
        List<String> data = input.asList();
        data.add(""); // Add empty line to process final group
    
        int result = 0;
        Set<Character> currentGroup = new HashSet<>();
        for (String ln : data) {
            if (ln.isEmpty()) {
                result += currentGroup.size();
                currentGroup.clear();
            } else {
                for (char c : ln.toCharArray())
                    currentGroup.add(c);
            }
        }
        return result;
    }
    
}
