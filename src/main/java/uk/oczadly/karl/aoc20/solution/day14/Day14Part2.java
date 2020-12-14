package uk.oczadly.karl.aoc20.solution.day14;

import uk.oczadly.karl.aoc20.PuzzleSolution;
import uk.oczadly.karl.aoc20.input.IllegalInputException;
import uk.oczadly.karl.aoc20.input.PuzzleInput;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
                mask.apply(Long.parseUnsignedLong(m.group(1)))
                        .forEach(v -> memory.put(v, val));
            }
        }
        
        // Sum all values in memory
        return memory.values().stream()
                .mapToLong(Long::longValue)
                .sum();
    }
    
    
    static class Mask {
        final String mask;
        /*
         * 'combiVals' contains each value which can be added to the result value (masked value, with all floating
         * bits set to zero) to calculate all the possible combinations of floating bits. Eg. a mask "X10X" would result
         * in 4 combination values: [0 (0000), 1 (0001), 8 (1000), 9 (1001)], so each call to apply() would return 4
         * different values for this mask.
         */
        final Set<Long> combiVals = new HashSet<>();
        
        public Mask(String mask) {
            this.mask = mask;
            calcCombinationVals();
        }
        
        /** Applies the mask to the given long value. Returns a stream of values. */
        public Stream<Long> apply(long val) {
            int size = mask.length();
            long sizeMask = (1L << size) - 1;
            
            // Process bits
            for (int i = 0; i < size; i++) {
                char c = mask.charAt(size - i - 1);
                if (c == '1') {
                    val = setBit(val, i, 1);
                } else if (c == 'X') {
                    val = setBit(val, i, 0); // Set floating bits to zero
                }
                val &= sizeMask; // Ensure number is same length as mask
            }
            
            // Calculate possible values
            final long finalVal = val;
            return combiVals.stream()
                    .map(v -> v + finalVal);
        }
    
        /** Calculates and populates the 'combiVals' set. */
        private void calcCombinationVals() {
            // Calculate the values of each floating bit, and add them to a list (eg. 2^i, where i is the bit index)
            List<Long> floatingVals = new ArrayList<>();
            for (int i = 0; i < mask.length(); i++)
                if (mask.charAt(mask.length() - i - 1) == 'X')
                    floatingVals.add(1L << i);
            // Calc combinations
            calcCombinationVals(0, floatingVals, 0);
        }
    
        /**
         * Recursive method to calculate all possible sequences.
         * @param val          the current value
         * @param floatingVals a list of values of each floating bit
         * @param index        the current index in the list
         */
        private void calcCombinationVals(long val, List<Long> floatingVals, int index) {
            if (index >= floatingVals.size()) return;
            long currentVal = floatingVals.get(index);
            combiVals.add(val);
            combiVals.add(val + currentVal);
            calcCombinationVals(val + currentVal, floatingVals, index + 1);
            calcCombinationVals(val, floatingVals, index + 1);
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
