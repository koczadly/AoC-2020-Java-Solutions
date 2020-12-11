package uk.oczadly.karl.aoc20.day10;

import uk.oczadly.karl.aoc20.Helper;

import java.util.*;

/**
 * @author Karl Oczadly
 */
public class Day10Part2 {
    
    public static void main(String[] args) {
        List<Integer> adapters = Helper.loadInput(10, Integer::parseInt);
        Collections.sort(adapters);
        
        long count = new Solver(adapters).solve();
        System.out.printf("Number of valid combinations = %d%n", count);
    }
    
    
    static class Solver {
        final List<Integer> adapters; // A list of adapters
        final Map<Integer, Long> cache = new HashMap<>(); // adapter index -> valid combinations
        final Stack<Integer> chainIndex = new Stack<>(); // Stores the current adapter chain (as indexes)
        
        public Solver(List<Integer> adapters) {
            this.adapters = adapters;
        }
    
        public long solve() {
            if (!chainIndex.isEmpty() && chainIndex.peek() == adapters.size() - 1)
                return 1; // Reached the final digit
            
            int prevJolts = chainIndex.isEmpty() ? 0 : adapters.get(chainIndex.peek());
            Long cachedVal = cache.get(prevJolts);
            if (cachedVal != null)
                return cachedVal;
            
            long validCount = 0;
            int i = chainIndex.isEmpty() ? 0 : chainIndex.peek() + 1;
            while (i < adapters.size()) {
                int adapterJolts = adapters.get(i);
                if (adapterJolts - prevJolts > 3) break; // No more possible values, outside of difference range
                
                chainIndex.push(i);
                validCount += solve();
                chainIndex.pop();
                i++;
            }
            cache.put(prevJolts, validCount);
            return validCount;
        }
    }

}
