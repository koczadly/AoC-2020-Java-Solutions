package uk.oczadly.karl.aoc20.solution.day8;

import uk.oczadly.karl.aoc20.NoSolutionFoundException;
import uk.oczadly.karl.aoc20.input.InputData;
import uk.oczadly.karl.aoc20.PuzzleSolution;

import java.util.List;

/**
 * Simplified version of Day8Part1
 */
public class Day8Part1Simple extends PuzzleSolution {
    
    public Day8Part1Simple() {
        super(8, 1, 2); // Initializes the day and part number
    }
    
    @Override
    public Object solve(InputData inputData) {
        List<String> input = inputData.asList();
        
        int instIndex = 0, acc = 0;
        boolean[] seen = new boolean[input.size()];
        while (instIndex < input.size()) {
            if (seen[instIndex])
                return acc; // Solution found
            seen[instIndex] = true;
            String[] inst = input.get(instIndex++).split(" ");
            int val = Integer.parseInt(inst[1]);
            switch (inst[0]) { // Operator
                case "acc": acc += val; break;
                case "jmp": instIndex += val - 1; break;
            }
        }
        throw new NoSolutionFoundException();
    }
    
}
