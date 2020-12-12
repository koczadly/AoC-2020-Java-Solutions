package uk.oczadly.karl.aoc20.solution.day12;

import uk.oczadly.karl.aoc20.PuzzleSolution;
import uk.oczadly.karl.aoc20.input.InputData;
import uk.oczadly.karl.aoc20.util.EnumIndexer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Karl Oczadly
 */
public class Day12Part1 extends PuzzleSolution {
    
    public Day12Part1() {
        super(12, 1); // Initializes the day and part number
    }
    
    @Override
    public Object solve(InputData input) {
        Ship ship = new Ship(Direction.EAST);
        
        input.asStream().forEachOrdered(ship::move);
        
        return Math.abs(ship.x) + Math.abs(ship.y);
    }
    
    
    static final Pattern INPUT_PATTERN = Pattern.compile("^(\\w)(\\d+)$");
    
    static class Ship {
        int x, y;
        Direction facing;
        
        public Ship(Direction facing) {
            this.facing = facing;
        }
        
    
        public void move(String str) {
            Matcher m = INPUT_PATTERN.matcher(str);
            if (!m.matches()) throw new IllegalArgumentException("Invalid input.");
            int val = Integer.parseInt(m.group(2));
            
            switch (m.group(1).charAt(0)) {
                case 'N':
                    y += val; break;
                case 'S':
                    y -= val; break;
                case 'E':
                    x += val; break;
                case 'W':
                    x -= val; break;
                case 'L':
                    facing = facing.turn(-val); break;
                case 'R':
                    facing = facing.turn(val); break;
                case 'F':
                    x += val * facing.xMult;
                    y += val * facing.yMult;
                    break;
                default:
                    throw new IllegalArgumentException("Unknown instruction code.");
            }
        }
    }
    
    
    enum Direction {
        NORTH(0, 0, 1), EAST(90, 1, 0), SOUTH(180, 0, -1), WEST(270, -1, 0);
        
        static final EnumIndexer<Direction, Integer> INDEX_YAW = new EnumIndexer<>(Direction.class, e -> e.yaw);
        
        final int yaw, xMult, yMult;
        Direction(int yaw, int xMult, int yMult) {
            this.yaw = yaw;
            this.xMult = xMult;
            this.yMult = yMult;
        }
        
        public Direction turn(int val) {
            return INDEX_YAW.valueOf(Math.floorMod(yaw + val, 360));
        }
    }
    
}
