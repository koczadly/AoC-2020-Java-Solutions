package uk.oczadly.karl.aoc20.solution.day14;

import uk.oczadly.karl.aoc20.PuzzleSolution;
import uk.oczadly.karl.aoc20.input.IllegalInputException;
import uk.oczadly.karl.aoc20.input.PuzzleInput;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Karl Oczadly
 */
public class Day14Part1 extends PuzzleSolution {
    
    public Day14Part1() {
        super(14, 1); // Initializes the day and part number
    }
    
    
    private static final Pattern PATTERN_MASK = Pattern.compile("^mask = ([10X]{36})$");
    private static final Pattern PATTERN_MEM = Pattern.compile("^mem\\[(\\d+)] = (\\d+)$");
    
    @Override
    public Object solve(PuzzleInput input) {
        Map<Long, Long> memory = new HashMap<>();
        BitMask bitMask = null;
        
        for (String line : input) {
            Matcher m = PATTERN_MASK.matcher(line);
            if (m.matches()) {
                // MASK
                bitMask = new BitMask(m.group(1));
            } else {
                if (bitMask == null) throw new IllegalInputException("Memory value before mask.");
                m = PATTERN_MEM.matcher(line);
                if (!m.matches()) throw new IllegalInputException();
                // MEM
                long val = bitMask.apply(Long.parseUnsignedLong(m.group(2))); // Calculate value after mask
                memory.put(Long.parseUnsignedLong(m.group(1)), val); // Save in memory
            }
        }
        
        // Sum all values in memory
        return memory.values().stream()
                .mapToLong(Long::longValue)
                .sum();
    }
    
    
    static class BitMask {
        final String mask;
        
        public BitMask(String mask) {
            this.mask = mask;
        }
    
        /** Applies the mask to the given long value. */
        public long apply(long val) {
            int size = mask.length();
            long sizeMask = (1L << size) - 1;
            
            for (int i = 0; i < size; i++) {
                char c = mask.charAt(size - i - 1);
                if (c == '0' || c == '1')
                    val = setBit(val, i, c == '0' ? 0 : 1);
                val &= sizeMask; // Ensure number is same length as mask
            }
            return val;
        }
    }
    
    /** Helper method to set a specific bit in a given long value. */
    static long setBit(long val, int bit, int bitVal) {
        if (bitVal == 0) {
            return val & ~(1L << bit); // Set to 0
        } else {
            return val | (1L << bit); // Set to 1
        }
    }

}
