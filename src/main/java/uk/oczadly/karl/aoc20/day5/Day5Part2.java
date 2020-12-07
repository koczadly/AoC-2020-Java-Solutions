package uk.oczadly.karl.aoc20.day5;

import uk.oczadly.karl.aoc20.Helper;

import java.util.Arrays;
import java.util.OptionalInt;

/**
 * @author Karl Oczadly
 */
public class Day5Part2 {
    
    public static void main(String[] args) throws Exception {
        // Populate seats array (true = occupied)
        boolean[][] seats = new boolean[128][8];
        Helper.streamInput(5)
                .map(Day5Part2::parseSeat)
                .forEach(seat -> seats[seat.row][seat.column] = true);
        
        // Find missing seat
        loop: for (int row = 1; row < seats.length-1; row++) { // Ignore end rows
            for (int col = 0; col < 8; col++) {
                // Ensure seat is unoccupied, and has an occupied seat in the rows before and after
                if (!seats[row][col] && seats[row - 1][col] && seats[row + 1][col]) {
                    System.out.printf("Missing seat: (%d, %d) = %d%n", row, col, getId(row, col));
                    break loop;
                }
            }
        }
    }
    
    
    private static Seat parseSeat(String str) {
        return new Seat(findNumber(str.substring(0, 7)), findNumber(str.substring(7, 10)));
    }
    
    private static int findNumber(String str) {
        int min = 0, max = (1 << str.length()) - 1;
        for (char c : str.toCharArray()) {
            int range = max - min + 1;
            switch (c) {
                case 'F':
                case 'L':
                    max -= range / 2;
                    break;
                case 'B':
                case 'R':
                    min += range / 2;
                    break;
                default: throw new IllegalArgumentException("Unknown movement.");
            }
        }
        if (min != max) throw new AssertionError("Unknown result value.");
        return min;
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
