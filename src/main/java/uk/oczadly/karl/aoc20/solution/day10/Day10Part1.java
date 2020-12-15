package uk.oczadly.karl.aoc20.solution.day10;

import uk.oczadly.karl.aoc20.input.PuzzleInput;
import uk.oczadly.karl.aoc20.PuzzleSolution;

import java.util.Collections;
import java.util.List;

/**
 * @author Karl Oczadly
 */
public class Day10Part1 extends PuzzleSolution {
    
    public Day10Part1() { super(10, 1); } // Initializes the day and part number
    
    @Override
    public Object solve(PuzzleInput input) {
        List<Integer> adapters = input.asList(Integer::parseInt);
        Collections.sort(adapters);
        
        int prev = 0, diff1 = 0, diff3 = 1;
        for (int adapter : adapters) {
            int diff = adapter - prev;
            if (diff == 1) diff1++;
            if (diff == 3) diff3++;
            prev = adapter;
        }
        
        return diff1 * diff3;
    }
    
}
