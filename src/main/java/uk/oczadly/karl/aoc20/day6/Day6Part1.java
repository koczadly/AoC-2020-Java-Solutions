package uk.oczadly.karl.aoc20.day6;

import uk.oczadly.karl.aoc20.Helper;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Karl Oczadly
 */
public class Day6Part1 {
    
    public static void main(String[] args) throws Exception {
        List<String> data = Helper.loadInput("6");
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
    
        System.out.printf("Result: %d%n", result);
    }
    
}
