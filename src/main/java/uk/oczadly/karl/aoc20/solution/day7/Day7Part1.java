package uk.oczadly.karl.aoc20.solution.day7;

import uk.oczadly.karl.aoc20.input.PuzzleInput;
import uk.oczadly.karl.aoc20.PuzzleSolution;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * @author Karl Oczadly
 */
public class Day7Part1 extends PuzzleSolution {
    
    static final String TARGET_COLOUR = "shiny gold";
    static final Pattern RULE_PATTERN = Pattern.compile(
            "(?:(^\\w+ \\w+) bags contain )|(?:\\G(\\d+) (\\w+ \\w+) bags?(?:, |.))");
    
    
    public Day7Part1() {
        super(7, 1); // Initializes the day and part number
    }
    
    @Override
    public Object solve(PuzzleInput input) {
        // Load the direct bag rules in
        Map<String, Set<String>> bagRules = loadBagRules(input.asStream());
    
        return bagRules.keySet().stream()
                .filter(c -> containsTargetColour(c, bagRules))
                .count();
    }
    
    /** Recursive method to check whether the given bag (or one of its children) contains 'shiny gold'. */
    private static boolean containsTargetColour(String name, Map<String, Set<String>> bagRules) {
        for (String subColour : bagRules.get(name))
            if (subColour.equals(TARGET_COLOUR) || containsTargetColour(subColour, bagRules)) return true;
        return false;
    }
    
    /** Loads all of the bag rules from the input data. */
    private static Map<String, Set<String>> loadBagRules(Stream<String> inputStream) {
        Map<String, Set<String>> bagRules = new HashMap<>();
        inputStream
                .map(RULE_PATTERN::matcher)
                .forEach(matcher -> {
                    String name = null;
                    Set<String> contains = new HashSet<>();
                    while (matcher.find()) {
                        if (name == null) {
                            // First match is the colour of the bag
                            name = matcher.group(1).toLowerCase();
                        } else {
                            // Subsequent matches are what the bag can contain
                            contains.add(matcher.group(3).toLowerCase());
                        }
                    }
                    bagRules.put(name, contains);
                });
        return bagRules;
    }
    
    
}
