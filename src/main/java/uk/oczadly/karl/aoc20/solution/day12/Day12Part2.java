package uk.oczadly.karl.aoc20.solution.day12;

import uk.oczadly.karl.aoc20.PuzzleSolution;
import uk.oczadly.karl.aoc20.input.PuzzleInput;
import uk.oczadly.karl.aoc20.util.InputUtil;

import java.util.regex.Matcher;

/**
 * @author Karl Oczadly
 */
public class Day12Part2 extends PuzzleSolution {
    
    public Day12Part2() {
        super(12, 2); // Initializes the day and part number
    }
    
    @Override
    public Object solve(PuzzleInput input) {
        Ship ship = new Ship(new Waypoint(10, 1)); // Starts with waypoint ahead 10 east, 1 north
    
        // Process each instruction
        input.asStream()
                .map(InputUtil.regexMapper("^(\\w)(\\d+)$"))
                .forEachOrdered(ship::readInstruction);
    
        // Return manhattan distance from origin (0, 0)
        return Math.abs(ship.x) + Math.abs(ship.y);
    }
    
    
    static class Waypoint {
        int xOffset, yOffset;
        
        public Waypoint(int x, int y) {
            this.xOffset = x;
            this.yOffset = y;
        }
        
        /** Rotates the waypoint clockwise around (0, 0) */
        public void rotate(int degrees) {
            if (degrees == 90 || degrees == 270) {
                int tmpX = xOffset;
                xOffset = yOffset;
                yOffset = -tmpX;
            }
            if (degrees == 180 || degrees == 270) {
                xOffset = -xOffset;
                yOffset = -yOffset;
            }
        }
    }
    
    static class Ship {
        int x, y;
        Waypoint wp;
        
        public Ship(Waypoint wp) {
            this.wp = wp;
        }
    
        /** Parses an instruction from the input, and processes it. */
        public void readInstruction(Matcher input) {
            int val = Integer.parseInt(input.group(2));
    
            switch (input.group(1).charAt(0)) {
                case 'N': // Move waypoint north
                    wp.yOffset += val; break;
                case 'S': // Move waypoint south
                    wp.yOffset -= val; break;
                case 'E': // Move waypoint east
                    wp.xOffset += val; break;
                case 'W': // Move waypoint west
                    wp.xOffset -= val; break;
                case 'L': // Rotate waypoint anticlockwise
                    wp.rotate(360 - val); break;
                case 'R': // Rotate waypoint clockwise
                    wp.rotate(val); break;
                case 'F': // Move ship towards waypoint
                    x += val * wp.xOffset;
                    y += val * wp.yOffset;
                    break;
                default:
                    throw new IllegalArgumentException("Unknown instruction code.");
            }
        }
    }
    
}
