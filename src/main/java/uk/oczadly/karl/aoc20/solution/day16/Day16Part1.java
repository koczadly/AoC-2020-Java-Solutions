package uk.oczadly.karl.aoc20.solution.day16;

import uk.oczadly.karl.aoc20.PuzzleSolution;
import uk.oczadly.karl.aoc20.input.IllegalInputException;
import uk.oczadly.karl.aoc20.input.PuzzleInput;
import uk.oczadly.karl.aoc20.util.InputUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Karl Oczadly
 */
public class Day16Part1 extends PuzzleSolution {
    
    public Day16Part1() { super(16, 1); } // Initializes the day and part number
    
    @Override
    public Object solve(PuzzleInput input) {
        // Parse input data
        TicketData data = new TicketData(input.asList());
        
        // Find and sum error values
        return data.nearbyTickets.stream()
                .flatMap(Collection::stream)
                .filter(val -> data.rules.stream()
                        .noneMatch(r -> r.isInRange(val)))
                .mapToInt(Integer::intValue)
                .sum();
    }
    
    
    static class TicketData {
        static final Pattern PATTERN_RULE = Pattern.compile("^([\\w ]+): (\\d+)-(\\d+) or (\\d+)-(\\d+)$");
        
        List<FieldRule> rules = new ArrayList<>();
        List<List<Integer>> nearbyTickets = new ArrayList<>();
    
        public TicketData(List<String> input) {
            int inputType = 0; // 0 = field rule, 1 = nearby tickets
            for (String line : input) {
                if (line.isEmpty()) continue; // Skip blank lines
                if (line.equals("nearby tickets:")) {
                    inputType = 1;
                } else if (inputType == 0) {
                    // Try field rule
                    Matcher ruleMatcher = PATTERN_RULE.matcher(line);
                    if (ruleMatcher.matches())
                        rules.add(new FieldRule(ruleMatcher));
                } else {
                    // Try as int list
                    nearbyTickets.add(InputUtil.split(line, ",", Integer::parseInt));
                }
            }
            if (rules.isEmpty() || nearbyTickets.isEmpty())
                throw new IllegalInputException();
        }
    }
    
    static class FieldRule {
        final String name;
        final int r1min, r1max, r2min, r2max;
    
        public FieldRule(Matcher m) {
            this.name = m.group(1);
            this.r1min = Integer.parseInt(m.group(2));
            this.r1max = Integer.parseInt(m.group(3));
            this.r2min = Integer.parseInt(m.group(4));
            this.r2max = Integer.parseInt(m.group(5));
        }
        
        public boolean isInRange(int val) {
            return (val >= r1min && val <= r1max) || (val >= r2min && val <= r2max);
        }
    }

}
