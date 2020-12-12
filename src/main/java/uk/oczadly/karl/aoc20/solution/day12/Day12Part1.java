package uk.oczadly.karl.aoc20.solution.day12;

import uk.oczadly.karl.aoc20.PuzzleSolution;
import uk.oczadly.karl.aoc20.input.InputData;
import uk.oczadly.karl.aoc20.util.EnumIndex;
import uk.oczadly.karl.aoc20.util.InputUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Karl Oczadly
 */
public class Day12Part1 extends PuzzleSolution {
    
    static final Pattern INPUT_PATTERN = Pattern.compile("^(\\w)(\\d+)$");
    
    
    public Day12Part1() {
        super(12, 1); // Initializes the day and part number
    }
    
    @Override
    public Object solve(InputData input) {
        Ship ship = new Ship(Direction.EAST); // Starts facing east
        
        // Process each instruction
        input.asStream()
                .map(InputUtil.mapRegex(INPUT_PATTERN))
                .forEachOrdered(ship::move);
        
        // Return manhattan distance from origin (0, 0)
        return Math.abs(ship.x) + Math.abs(ship.y);
    }
    
    
    static class Ship {
        int x, y;
        Direction facing;
        
        public Ship(Direction facing) {
            this.facing = facing;
        }
    
        /** Parses an instruction from the input, and processes it. */
        public void move(Matcher input) {
            char instCode = input.group(1).charAt(0);
            int val = Integer.parseInt(input.group(2));
            
            Direction dir = Direction.INDEX_CHAR.valueOfNullable(instCode);
            if (dir != null) {
                // Instruction is a direction
                x += dir.xMult * val;
                y += dir.yMult * val;
            } else if (instCode == 'L' || instCode == 'R') {
                // Turn direction
                facing = facing.turn((instCode == 'R') ? val : -val);
            } else if (instCode == 'F') {
                // Move forward
                x += val * facing.xMult;
                y += val * facing.yMult;
            } else {
                throw new IllegalArgumentException("Unknown instruction code.");
            }
        }
    }
    
    
    enum Direction {
        NORTH(0, 0, 1), EAST(90, 1, 0), SOUTH(180, 0, -1), WEST(270, -1, 0);
    
        static final EnumIndex<Direction, Character> INDEX_CHAR =
                new EnumIndex<>(Direction.class, e -> e.name().charAt(0));
        static final EnumIndex<Direction, Integer> INDEX_YAW = new EnumIndex<>(Direction.class, e -> e.yaw);
        
        final int yaw, xMult, yMult;
        Direction(int yaw, int xMult, int yMult) {
            this.yaw = yaw;
            this.xMult = xMult;
            this.yMult = yMult;
        }
        
        /** Returns the direction from turning this direction the specified degrees */
        public Direction turn(int degrees) {
            return INDEX_YAW.valueOf(Math.floorMod(yaw + degrees, 360));
        }
    }
    
}
