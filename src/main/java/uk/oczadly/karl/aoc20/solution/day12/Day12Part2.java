package uk.oczadly.karl.aoc20.solution.day12;

import uk.oczadly.karl.aoc20.PuzzleSolution;
import uk.oczadly.karl.aoc20.input.InputData;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Karl Oczadly
 */
public class Day12Part2 extends PuzzleSolution {
    
    public Day12Part2() {
        super(12, 2); // Initializes the day and part number
    }
    
    @Override
    public Object solve(InputData input) {
        Ship ship = new Ship(new Waypoint(10, 1)); // Starts with waypoint ahead 10 east, 1 north
    
        // Process each instruction
        input.asStream()
                .forEachOrdered(ship::readInstruction);
    
        // Return manhattan distance from origin (0, 0)
        return Math.abs(ship.x) + Math.abs(ship.y);
    }
    
    
    static final Pattern INPUT_PATTERN = Pattern.compile("^(\\w)(\\d+)$");
    
    
    static class Waypoint {
        int xOff, yOff;
        
        public Waypoint(int x, int y) {
            this.xOff = x;
            this.yOff = y;
        }
        
        public void rotate(int degrees) {
            degrees = Math.floorMod(degrees, 360); // Convert to positive number (0, 90, 180, 270)
            int x = xOff; // Temp copy of xOff
            switch (degrees) {
                case 180:
                    xOff = -xOff;
                    yOff = -yOff;
                    break;
                case 90:
                    xOff = yOff;
                    yOff = -x;
                    break;
                case 270:
                    xOff = -yOff;
                    yOff = x;
                    break;
                case 0: break;
                default: throw new IllegalArgumentException("Unsupported rotation degrees.");
            }
        }
    }
    
    static class Ship {
        int x, y;
        Waypoint wp;
        
        public Ship(Waypoint wp) {
            this.wp = wp;
        }
    
        public void readInstruction(String str) {
            Matcher m = INPUT_PATTERN.matcher(str);
            if (!m.matches()) throw new IllegalArgumentException("Invalid input.");
            int val = Integer.parseInt(m.group(2));
    
            switch (m.group(1).charAt(0)) {
                case 'N': // Move waypoint north
                    wp.yOff += val; break;
                case 'S': // Move waypoint south
                    wp.yOff -= val; break;
                case 'E': // Move waypoint east
                    wp.xOff += val; break;
                case 'W': // Move waypoint west
                    wp.xOff -= val; break;
                case 'L': // Rotate waypoint anticlockwise
                    wp.rotate(-val); break;
                case 'R': // Rotate waypoint clockwise
                    wp.rotate(val); break;
                case 'F': // Move ship towards waypoint
                    x += val * wp.xOff;
                    y += val * wp.yOff;
                    break;
                default:
                    throw new IllegalArgumentException("Unknown instruction code.");
            }
        }
    }
    
}
