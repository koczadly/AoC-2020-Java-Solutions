package uk.oczadly.karl.aoc20.solution.day5;

import uk.oczadly.karl.aoc20.input.IllegalInputException;
import uk.oczadly.karl.aoc20.input.PuzzleInput;
import uk.oczadly.karl.aoc20.input.NoValidSolutionException;
import uk.oczadly.karl.aoc20.PuzzleSolution;

/**
 * @author Karl Oczadly
 */
public class Day5Part2 extends PuzzleSolution {
    
    public Day5Part2() {
        super(5, 2); // Initializes the day and part number
    }
    
    @Override
    public Object solve(PuzzleInput input) {
        // Populate seats array (true = occupied)
        boolean[][] seats = new boolean[128][8];
        input.asStream()
                .map(Day5Part2::parseSeat)
                .forEach(seat -> seats[seat.row][seat.column] = true);
        
        // Find missing seat
        for (int row = 1; row < seats.length-1; row++) { // Ignore end rows
            for (int col = 0; col < 8; col++) {
                // Ensure seat is unoccupied, and has an occupied seat in the rows before and after
                if (!seats[row][col] && seats[row - 1][col] && seats[row + 1][col]) {
                    return getId(row, col);
                }
            }
        }
        throw new NoValidSolutionException();
    }
    
    
    /** Parses a Seat object (row and column) from a string input */
    private static Seat parseSeat(String str) {
        return new Seat(
                calcBinaryNumber(str.substring(0, 7)),
                calcBinaryNumber(str.substring(7, 10)));
    }
    
    /** Calculates the binary number from a sequence of steps. */
    private static int calcBinaryNumber(String str) {
        int lower = 0, upper = (1 << str.length()) - 1;
        for (char c : str.toCharArray()) {
            int range = upper - lower + 1;
            switch (c) {
                case 'F': case 'L':
                    upper -= range / 2; break;
                case 'B': case 'R':
                    lower += range / 2; break;
                default: throw new IllegalInputException("Unknown movement char.");
            }
        }
        if (lower != upper) throw new AssertionError();
        return lower;
    }
    
    static class Seat {
        final int row, column;
    
        public Seat(int row, int column) {
            this.row = row;
            this.column = column;
        }
    }
    
    private static int getId(int row, int column) {
        return row * 8 + column;
    }
    
}
