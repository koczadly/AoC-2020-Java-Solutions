package uk.oczadly.karl.aoc20.day6;

import uk.oczadly.karl.aoc20.Helper;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * @author Karl Oczadly
 */
public class Day6Part2 {
    
    public static void main(String[] args) throws Exception {
        List<String> data = Helper.loadInput("6");
        data.add(""); // Add empty line to process final group
    
        int result = 0;
        boolean newGroup = true;
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
                    // Get set of current persons chars
                    Set<Character> currentChars = new HashSet<>();
                    for (char c : ln.toCharArray())
                        currentChars.add(c);
                    // Remove missing chars from set
                    currentGroup.removeIf(c -> !currentChars.contains(c));
                }
            }
        }
    
        System.out.printf("Result: %d%n", result);
    }
    
}
