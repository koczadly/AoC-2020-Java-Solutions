package uk.oczadly.karl.aoc20.solution.day13;

import uk.oczadly.karl.aoc20.input.NoValidSolutionException;
import uk.oczadly.karl.aoc20.PuzzleSolution;
import uk.oczadly.karl.aoc20.input.PuzzleInput;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Karl Oczadly
 */
public class Day13Part1 extends PuzzleSolution {
    
    public Day13Part1() {
        super(13, 1); // Initializes the day and part number
    }
    
    @Override
    public Object solve(PuzzleInput input) {
        // Load input
        int earliestTimestamp = Integer.parseInt(input.nextLine());
        List<Integer> busIds = Arrays.stream(input.nextLine().split(","))
                .filter(s -> !s.equals("x"))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        
        // Process
        for (int ts = earliestTimestamp; ts < Integer.MAX_VALUE; ts++) {
            for (int busSchedule : busIds)
                if (ts % busSchedule == 0)
                    return busSchedule * (ts - earliestTimestamp);
        }
        throw new NoValidSolutionException();
    }

}
