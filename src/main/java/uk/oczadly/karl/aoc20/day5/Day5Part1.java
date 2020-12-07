package uk.oczadly.karl.aoc20.day5;

import uk.oczadly.karl.aoc20.Helper;

import java.util.OptionalInt;

/**
 * @author Karl Oczadly
 */
public class Day5Part1 {
    
    public static void main(String[] args) throws Exception {
        OptionalInt maxSeatId = Helper.streamInput(5)
                .map(Day5Part1::parseSeat)
                .mapToInt(Seat::getId)
                .max();

        maxSeatId.ifPresent(id -> System.out.printf("Max seat ID: %d%n", id));
    }
    
    
    /** Parses a Seat object (row and column) from a string input */
    private static Seat parseSeat(String str) {
        return new Seat(calcBinaryNumber(str.substring(0, 7)), calcBinaryNumber(str.substring(7, 10)));
    }
    
    /** Calculates the binary number from a sequence of steps. */
    private static int calcBinaryNumber(String str) {
        int lower = 0, upper = (1 << str.length()) - 1;
        for (char c : str.toCharArray()) {
            int range = upper - lower + 1;
            switch (c) {
                case 'F':
                case 'L':
                    upper -= range / 2;
                    break;
                case 'B':
                case 'R':
                    lower += range / 2;
                    break;
                default: throw new IllegalArgumentException("Unknown movement.");
            }
        }
        if (lower != upper) throw new AssertionError("Unknown result value.");
        return lower;
    }
    
    static class Seat {
        final int row, column;
    
        public Seat(int row, int column) {
            this.row = row;
            this.column = column;
        }
    
        public int getId() {
            return row * 8 + column;
        }
    }
    
}
