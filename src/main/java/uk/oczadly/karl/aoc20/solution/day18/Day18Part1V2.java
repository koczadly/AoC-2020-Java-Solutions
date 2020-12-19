package uk.oczadly.karl.aoc20.solution.day18;

import uk.oczadly.karl.aoc20.PuzzleSolution;
import uk.oczadly.karl.aoc20.input.IllegalInputException;
import uk.oczadly.karl.aoc20.input.PuzzleInput;
import uk.oczadly.karl.aoc20.util.EnumIndex;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.function.LongBinaryOperator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Karl Oczadly
 *
 * OOP variant of day 18 part 1 solution.
 */
public class Day18Part1V2 extends PuzzleSolution {
    
    public Day18Part1V2() { super(18, 1, 2); } // Initializes the day and part number
    
    @Override
    public Object solve(PuzzleInput input) {
        return input.asStream()
                .map(Day18Part1V2::parseExpression)
                .mapToLong(Expression::calculate)
                .sum();
    }
    
    
    static final Pattern PATTERN_EXP_SCAN = Pattern.compile("\\d+|[+*()]");
    
    static Expression parseExpression(String expr) {
        Matcher m = PATTERN_EXP_SCAN.matcher(expr);
        
        Stack<ExpressionSection> stack = new Stack<>(); // Tracks the current level
        ExpressionSection mainSection = stack.push(new ExpressionSection(Operator.PLUS));
        Operator prevOp = Operator.PLUS;
        
        while (m.find()) {
            String component = m.group();
            Operator parsedOp = Operator.INDEX_CHAR.valueOfNullable(component.charAt(0));
            if (parsedOp != null) {
                // Set next operator
                prevOp = parsedOp;
            } else if (component.equals("(")) {
                // Enter new level
                ExpressionSection nextLevel = new ExpressionSection(prevOp);
                stack.peek().addChild(nextLevel); // Add child to current level
                stack.push(nextLevel); // Add to stack
                prevOp = Operator.PLUS; // Reset operator
            } else if (component.equals(")")) {
                // Back out a level
                stack.pop();
            } else {
                // Assume integer constant
                stack.peek().addChild(new ExpressionInteger(prevOp, Long.parseLong(component)));
                prevOp = Operator.PLUS; // Reset operator
            }
        }
        if (stack.size() != 1) throw new IllegalInputException("Invalid expression.");
        return mainSection;
    }
    
    
    /** A component of an expression. */
    static abstract class Expression {
        private final Operator op;
        
        public Expression(Operator op) {
            this.op = op;
        }
        
        public Operator getOperator() {
            return op;
        }
        
        abstract long calculate();
    }
    
    /** Represents a constant integer value. */
    static class ExpressionInteger extends Expression {
        private final long val;
        
        public ExpressionInteger(Operator op, long val) {
            super(op);
            this.val = val;
        }
        
        @Override
        public long calculate() {
            return val;
        }
    }
    
    /** Represents a level (pair of brackets) in an expression. */
    static class ExpressionSection extends Expression {
        private final List<Expression> children = new ArrayList<>();
        
        public ExpressionSection(Operator op) {
            super(op);
        }
        
        public void addChild(Expression component) {
            children.add(component);
        }
        
        @Override
        public long calculate() {
            if (children.isEmpty()) throw new IllegalInputException("Section contains no child elements.");
            long result = 0;
            for (Expression component : children)
                result = component.getOperator().apply(component.calculate(), result);
            return result;
        }
    }
    
    enum Operator {
        PLUS     ('+', Long::sum),
        MULTIPLY ('*', (a, b) -> a * b);
        
        static final EnumIndex<Operator, Character> INDEX_CHAR = new EnumIndex<>(Operator.class, e -> e.symbol);
        
        final char symbol;
        final LongBinaryOperator func;
        Operator(char symbol, LongBinaryOperator func) {
            this.symbol = symbol;
            this.func = func;
        }
        
        public long apply(long a, long b) {
            return func.applyAsLong(a, b);
        }
    }
    
}
