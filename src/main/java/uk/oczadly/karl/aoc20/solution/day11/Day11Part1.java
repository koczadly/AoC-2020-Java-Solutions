package uk.oczadly.karl.aoc20.solution.day11;

import uk.oczadly.karl.aoc20.input.InputData;
import uk.oczadly.karl.aoc20.PuzzleSolution;

import java.util.Arrays;
import java.util.List;

/**
 * @author Karl Oczadly
 */
public class Day11Part1 extends PuzzleSolution {
    
    public Day11Part1() {
        super(11, 1); // Initializes the day and part number
    }
    
    @Override
    public Object solve(InputData inputData) {
        SeatMap seatMap = SeatMap.load(inputData.asList());
        while (seatMap.nextIteration());
        
        return Arrays.stream(seatMap.seatData).flatMap(Arrays::stream)
                .filter(s -> s == SeatState.SEAT_OCCUPIED)
                .count();
    }
    
    
    static class SeatMap {
        private SeatState[][] seatData, nextSeatData;
        private final int width, height;
    
        public SeatMap(SeatState[][] seatData, int width, int height) {
            this.seatData = seatData;
            this.nextSeatData = new SeatState[height][width];
            this.width = width;
            this.height = height;
        }
        
        
        public SeatState getSeat(int row, int col) {
            if (row < 0 || row >= height || col < 0 || col >= width)
                return SeatState.FLOOR;
            return seatData[row][col];
        }
    
        public boolean nextIteration() {
            // Solve each seat
            boolean hasChanged = false;
            for (int row = 0; row < height; row++) {
                for (int col = 0; col < width; col++) {
                    SeatState currentState = getSeat(row, col);
                    SeatState nextState = nextIteration(row, col);
                    nextSeatData[row][col] = nextState;
                    if (currentState != nextState)
                        hasChanged = true;
                }
            }
            
            // Apply changes
            SeatState[][] oldArr = seatData; // Reuse existing array
            seatData = nextSeatData;
            nextSeatData = oldArr;
            return hasChanged;
        }
    
        private SeatState nextIteration(int row, int col) {
            SeatState state = getSeat(row, col);
            if (state != SeatState.FLOOR) {
                // Count neighbours
                int neighbours = 0;
                for (int r = -1; r <= 1; r++) {
                    for (int c = -1; c <= 1; c++) {
                        if (r == 0 && c == 0) continue;
                        if (getSeat(row + r, col + c) == SeatState.SEAT_OCCUPIED)
                            neighbours++;
                    }
                }
                // Mutate
                if (state == SeatState.SEAT_EMPTY && neighbours == 0) {
                    return SeatState.SEAT_OCCUPIED;
                } else if (state == SeatState.SEAT_OCCUPIED && neighbours >= 4) {
                    return SeatState.SEAT_EMPTY;
                }
            }
            return state;
        }
        
        public static SeatMap load(List<String> input) {
            int width = input.get(0).length();
            SeatState[][] arr = new SeatState[input.size()][width];
            for (int row = 0; row < input.size(); row++) {
                String rowData = input.get(row);
                for (int col = 0; col < rowData.length(); col++) {
                    arr[row][col] = SeatState.valueOfChar(rowData.charAt(col));
                }
            }
            return new SeatMap(arr, width, input.size());
        }
    }
    
    
    enum SeatState {
        FLOOR         ('.'),
        SEAT_EMPTY    ('L'),
        SEAT_OCCUPIED ('#');
        
        final char character;
        SeatState(char character) {
            this.character = character;
        }
        
        public static SeatState valueOfChar(char c) {
            for (SeatState seat : SeatState.values())
                if (seat.character == c) return seat;
            throw new IllegalArgumentException("Unknown seat char.");
        }
    }
    
}
