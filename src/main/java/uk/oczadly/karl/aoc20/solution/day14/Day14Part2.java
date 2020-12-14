package uk.oczadly.karl.aoc20.solution.day14;

import uk.oczadly.karl.aoc20.PuzzleSolution;
import uk.oczadly.karl.aoc20.input.IllegalInputException;
import uk.oczadly.karl.aoc20.input.PuzzleInput;

import java.lang.reflect.Array;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Karl Oczadly
 */
public class Day14Part2 extends PuzzleSolution {
    
    public Day14Part2() {
        super(14, 2); // Initializes the day and part number
    }
    
    
    private static final Pattern PATTERN_MASK = Pattern.compile("^mask = ([10X]{36})$");
    private static final Pattern PATTERN_MEM = Pattern.compile("^mem\\[(\\d+)] = (\\d+)$");
    
    @Override
    public Object solve(PuzzleInput input) {
        Map<Long, Long> memory = new HashMap<>();
        Mask mask = null;
        
        for (String line : input) {
            Matcher m = PATTERN_MASK.matcher(line);
            if (m.matches()) {
                // MASK
                mask = new Mask(m.group(1));
            } else {
                if (mask == null) throw new IllegalInputException("Memory value before mask.");
                m = PATTERN_MEM.matcher(line);
                if (!m.matches()) throw new IllegalInputException();
                // MEM
                long val = Long.parseUnsignedLong(m.group(2));
                for (long addr : mask.apply(Long.parseUnsignedLong(m.group(1)))) {
                    memory.put(addr, val); // Save in memory
                }
            }
        }
        
        // Sum all values in memory
        return memory.values().stream()
                .mapToLong(Long::longValue)
                .sum();
    }
    
    
    static class Mask {
        final String mask;
        public Mask(String mask) {
            this.mask = mask;
        }
        
        public Set<Long> apply(long val) {
            int size = mask.length();
            long sizeMask = (1L << size) - 1;
            List<Long> floatingBitVals = new ArrayList<>(); // Tracks the value associated with each floating bit
            
            // Compute bits
            for (int i = 0; i < size; i++) {
                char c = mask.charAt(size - i - 1);
                if (c == '1') {
                    val = setBit(val, i, 1);
                } else if (c == 'X') {
                    val = setBit(val, i, 0);
                    floatingBitVals.add(1L << i);
                }
                val &= sizeMask; // Ensure number is same length as mask
            }
            
            // Calculate possible values
            Set<Long> result = new HashSet<>();
            calculatePossibleVals(val, floatingBitVals, 0, result);
            return result;
        }
    }
    
    /** Sets a specific bit in a given long value to 1 or 0. */
    static long setBit(long val, int bit, int bitVal) {
        if (bitVal == 0) {
            return val & ~(1L << bit); // Set to 0
        } else {
            return val | (1L << bit); // Set to 1
        }
    }
    
    /**
     * Recursive method to calculate all possible sequences.
     * For the first call, 'val' should be the value with all floating bits set to zero, index should be 0 and
     * 'result' should be empty.
     * @param val          the current value
     * @param floatingVals a list of values of each floating bit
     * @param index        the current index in the list
     * @param result       a set of calculated values
     */
    static void calculatePossibleVals(long val, List<Long> floatingVals, int index, Set<Long> result) {
        if (index >= floatingVals.size()) return;
        long currentVal = floatingVals.get(index);
        result.add(val);
        result.add(val + currentVal);
        calculatePossibleVals(val + currentVal, floatingVals, index + 1, result);
        calculatePossibleVals(val, floatingVals, index + 1, result);
    }

}
