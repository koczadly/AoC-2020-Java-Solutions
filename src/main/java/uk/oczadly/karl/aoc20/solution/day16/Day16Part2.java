package uk.oczadly.karl.aoc20.solution.day16;

import uk.oczadly.karl.aoc20.PuzzleSolution;
import uk.oczadly.karl.aoc20.input.IllegalInputException;
import uk.oczadly.karl.aoc20.input.PuzzleInput;
import uk.oczadly.karl.aoc20.util.InputUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Karl Oczadly
 */
public class Day16Part2 extends PuzzleSolution {
    
    public Day16Part2() { super(16, 2); } // Initializes the day and part number
    
    @Override
    public Object solve(PuzzleInput input) {
        // Parse input data
        TicketData data = new TicketData(input.asList());
        
        // Find possible fields
        FieldRule[] fieldTypes = calculateFields(data);
        
        // Find solution
        return IntStream.range(0, fieldTypes.length)
                .filter(i -> fieldTypes[i].name.startsWith("departure"))
                .mapToLong(data.ticket::get)
                .reduce(1, (a, b) -> a * b);
    }
    
    
    /** Attempts to find the fields corresponding to each index. Each index in the returned array represents the
     *  index of data in each ticket. */
    static FieldRule[] calculateFields(TicketData data) {
        // Determine matching fields
        List<Set<FieldRule>> allMatchingFields = new ArrayList<>(); // Tracks the possible field types for each index
        for (int fi = 0; fi < data.rules.size(); fi++)
            allMatchingFields.add(new HashSet<>(data.rules)); // Populate allPossibleFields
        
        ticketLoop: for (List<Integer> ticketVals : data.allTickets) {
            List<Set<FieldRule>> currMatchingFields = new ArrayList<>();
            for (int fi = 0; fi < ticketVals.size(); fi++) { // Field index
                int fieldVal = ticketVals.get(fi);
                Set<FieldRule> matchingRules = allMatchingFields.get(fi).stream()
                        .filter(r -> r.isInRange(fieldVal))
                        .collect(Collectors.toSet());
                if (matchingRules.isEmpty())
                    continue ticketLoop; // Ticket is invalid - value doesn't match any field type
                currMatchingFields.add(matchingRules);
            }
            allMatchingFields = currMatchingFields; // Update allMatchingFields
        }
        
        // Infer other field types from what we know
        FieldRule[] result = new FieldRule[allMatchingFields.size()];
        boolean updated;
        do {
            updated = false;
            for (int fi = 0; fi < data.rules.size(); fi++) {
                Set<FieldRule> fields = allMatchingFields.get(fi);
                if (fields.size() == 1) {
                    // Found single match for field index
                    FieldRule rule = fields.iterator().next(); // Get only item from set
                    result[fi] = rule;
                    updated = true;
                    // Remove field from other matching indexes (as it belongs to this index)
                    allMatchingFields.forEach(s -> s.remove(rule));
                }
            }
        } while (updated);
        return result;
    }
    
    static class TicketData {
        static final Pattern PATTERN_RULE = Pattern.compile("^([\\w ]+): (\\d+)-(\\d+) or (\\d+)-(\\d+)$");
        
        List<FieldRule> rules = new ArrayList<>();
        List<Integer> ticket;
        List<List<Integer>> allTickets = new ArrayList<>();
    
        public TicketData(List<String> input) {
            int inputType = 0; // 0 = field rule, 1 = your ticket, 2 = nearby tickets
            for (String line : input) {
                if (line.isEmpty()) continue; // Skip blank lines
                if (line.equals("your ticket:")) {
                    inputType = 1;
                } else if (line.equals("nearby tickets:")) {
                    inputType = 2;
                } else if (inputType == 0) {
                    // Try field rule
                    Matcher ruleMatcher = PATTERN_RULE.matcher(line);
                    if (ruleMatcher.matches())
                        rules.add(new FieldRule(ruleMatcher));
                } else {
                    // Try as int list
                    List<Integer> ints = InputUtil.split(line, ",", Integer::parseInt);
                    if (inputType == 1) {
                        ticket = ints;
                    } else {
                        allTickets.add(ints);
                    }
                }
            }
            allTickets.add(ticket); // Include our ticket in allTickets
            if (rules.isEmpty() || allTickets.isEmpty() || ticket == null)
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
