package uk.oczadly.karl.aoc20.solution.day15;

import uk.oczadly.karl.aoc20.PuzzleSolution;
import uk.oczadly.karl.aoc20.input.PuzzleInput;
import uk.oczadly.karl.aoc20.util.InputUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Karl Oczadly
 */
public class Day15Part1 extends PuzzleSolution {
    
    public Day15Part1() { super(15, 1); } // Initializes the day and part number
    
     @Override
    public Object solve(PuzzleInput input) {
        return new Solver(
                InputUtil.split(input.asString(), ",", Integer::parseInt)) // Load input as list of ints
                .solve(2020); // Solve
    }
    
    
    static class Solver {
        private final Map<Integer, LastSpoken> memory = new HashMap<>(); // Number -> last spoken indexes
        private final List<Integer> input;
        
        public Solver(List<Integer> input) {
            this.input = input;
        }
    
        public int solve(int iterations) {
            int num = 0; // Tracks the previous number
            for (int i = 1; i <= iterations; i++) {
                if (i <= input.size()) {
                    num = input.get(i - 1); // Starting number
                } else {
                    num = getLastSpoken(num).getDifference(); // Use difference value of previous number
                }
                getLastSpoken(num).speak(i);
            }
            return num;
        }
    
        private LastSpoken getLastSpoken(int num) {
            return memory.computeIfAbsent(num, k -> new LastSpoken());
        }
    }
    
    static class LastSpoken {
        int lastTurn, beforeLastTurn;
        
        /** 'Speaks' the current number, and sets/shifts the turn indexes. */
        public void speak(int turn) {
            this.beforeLastTurn = this.lastTurn;
            this.lastTurn = turn;
        }
        
        /** Returns the differences in index, or 0 if only spoken once. */
        public int getDifference() {
            return beforeLastTurn == 0 ? 0 : lastTurn - beforeLastTurn;
        }
    }

}
