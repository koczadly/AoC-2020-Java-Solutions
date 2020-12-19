package uk.oczadly.karl.aoc20.solution.day18;

import uk.oczadly.karl.aoc20.PuzzleSolution;
import uk.oczadly.karl.aoc20.input.PuzzleInput;

import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Karl Oczadly
 *
 *
 */
public class Day18Part1 extends PuzzleSolution {
    
    public Day18Part1() { super(18, 1, 1); } // Initializes the day and part number
    
    @Override
    public Object solve(PuzzleInput input) {
        return input.asStream()
                .mapToLong(Day18Part1::evaluate)
                .sum();
    }
    
    
    static final Pattern PATTERN_EXP_SCAN = Pattern.compile("\\d+|[+*()]");
    
    static long evaluate(String expr) {
        Matcher m = PATTERN_EXP_SCAN.matcher(expr);
        Stack<StackFrame> stack = new Stack<>();
        StackFrame mainFrame = stack.push(new StackFrame());
        
        while (m.find()) {
            StackFrame frame = stack.peek();
            String component = m.group();
    
            switch (component) {
                case "+": // Plus operator
                    frame.prevOp = Operator.PLUS;
                    break;
                case "*": // Multiply operator
                    frame.prevOp = Operator.MULTIPLY;
                    break;
                case "(": // Create new frame
                    stack.push(new StackFrame());
                    break;
                case ")": // End of frame
                    stack.pop();
                    stack.peek().accumulateValue(frame.currentVal);
                    break;
                default: // Integer constant
                    frame.accumulateValue(Long.parseLong(component));
                    break;
            }
        }
        return mainFrame.currentVal;
    }
    
    static class StackFrame {
        Operator prevOp = Operator.PLUS;
        long currentVal = 0;
        
        public void accumulateValue(long val) {
            currentVal = prevOp.apply(currentVal, val);
        }
    }
    
    enum Operator {
        PLUS, MULTIPLY;
        
        public long apply(long a, long b) {
            switch (this) {
                case PLUS: return a + b;
                case MULTIPLY: return a * b;
                default: throw new AssertionError();
            }
        }
    }
    
}
