package uk.oczadly.karl.aoc20.day10;

import uk.oczadly.karl.aoc20.Helper;

import java.util.Collections;
import java.util.List;

/**
 * @author Karl Oczadly
 */
public class Day10Part1 {
    
    public static void main(String[] args) {
        List<Integer> adapters = Helper.loadInput(10, Integer::parseInt);
        Collections.sort(adapters);
        
        int prev = 0, diff1 = 0, diff3 = 1;
        for (int adapter : adapters) {
            int diff = adapter - prev;
            if (diff == 1) diff1++;
            if (diff == 3) diff3++;
            prev = adapter;
        }
        
        System.out.printf("Solution = %d%n", diff1 * diff3);
    }
    
}
