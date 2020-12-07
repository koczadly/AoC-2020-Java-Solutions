package uk.oczadly.karl.aoc20.day7;

import uk.oczadly.karl.aoc20.Helper;

import java.util.*;
import java.util.regex.Pattern;

/**
 * @author Karl Oczadly
 */
public class Day7Part1 {
    
    static final String TARGET_COLOUR = "shiny gold";
    static final Pattern RULE_PATTERN = Pattern.compile(
            "(?:(^\\w+ \\w+) bags contain )|(?:\\G(\\d+) (\\w+ \\w+) bags?(?:, |.))");
    
    
    
    public static void main(String[] args) {
        // Load the direct bag rules in
        Map<String, Set<String>> bagRules = loadBagRules();
        
        int count = 0;
        for (String colour : bagRules.keySet())
            if (containsTargetColour(colour, bagRules)) count++;
    
        System.out.printf("Colours which can contain '%s': %d%n", TARGET_COLOUR, count);
    }
    
    /** Recursive method to check whether the given bag (or one of its children) contains 'shiny gold'. */
    private static boolean containsTargetColour(String name, Map<String, Set<String>> bagRules) {
        for (String subColour : bagRules.get(name))
            if (subColour.equals(TARGET_COLOUR) || containsTargetColour(subColour, bagRules)) return true;
        return false;
    }
    
    /** Loads all of the bag rules from the input data. */
    private static Map<String, Set<String>> loadBagRules() {
        Map<String, Set<String>> bagRules = new HashMap<>();
        Helper.streamInput(7)
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
