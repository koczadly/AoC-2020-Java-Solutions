package uk.oczadly.karl.aoc20.solution.day6;

import uk.oczadly.karl.aoc20.input.PuzzleInput;
import uk.oczadly.karl.aoc20.PuzzleSolution;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Karl Oczadly
 */
public class Day6Part2 extends PuzzleSolution {
    
    public Day6Part2() {
        super(6, 2); // Initializes the day and part number
    }
    
    @Override
    public Object solve(PuzzleInput input) {
        List<String> data = input.asList();
        data.add(""); // Add empty line to process final group
    
        int result = 0;
        boolean newGroup = true; // Ensure first entry of group adds to the list instead of subtracting
        Set<Character> currentGroup = new HashSet<>();
        
        for (String ln : data) {
            if (ln.isEmpty()) {
                result += currentGroup.size();
                currentGroup.clear();
                newGroup = true;
            } else {
                if (newGroup) {
                    newGroup = false;
                    for (char c : ln.toCharArray())
                        currentGroup.add(c);
                } else {
                    if (currentGroup.isEmpty()) continue;
                    // Get set of current persons chars
                    Set<Character> currentChars = ln.chars().mapToObj(i -> (char)i).collect(Collectors.toSet());
                    // Remove missing chars from set
                    currentGroup.removeIf(c -> !currentChars.contains(c));
                }
            }
        }
        return result;
    }
    
}
