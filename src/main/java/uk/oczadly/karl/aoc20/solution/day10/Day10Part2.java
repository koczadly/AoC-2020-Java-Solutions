package uk.oczadly.karl.aoc20.solution.day10;

import uk.oczadly.karl.aoc20.input.PuzzleInput;
import uk.oczadly.karl.aoc20.PuzzleSolution;

import java.util.*;

/**
 * @author Karl Oczadly
 */
public class Day10Part2 extends PuzzleSolution {
    
    public Day10Part2() {
        super(10, 2); // Initializes the day and part number
    }
    
    @Override
    public Object solve(PuzzleInput input) {
        List<Integer> adapters = input.asList(Integer::parseInt);
        Collections.sort(adapters);
        return new Solver(adapters).solve();
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
