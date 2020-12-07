package uk.oczadly.karl.aoc20.day7;

import uk.oczadly.karl.aoc20.Helper;

import java.util.*;
import java.util.regex.Pattern;

/**
 * @author Karl Oczadly
 */
public class Day7Part1 {
    
    static final Pattern RULE_PATTERN = Pattern.compile(
            "(?:(^\\w+ \\w+) bags contain )|(?:\\G(\\d+) (\\w+ \\w+) bags?(?:, |.))");
    
    
    public static void main(String[] args) {
        // Load bag rules in
        Map<String, Set<String>> bagRules = loadBagRules();
        
        // Calculate all the bag colours they can recursively hold
        Map<String, Set<String>> bagRulesRecursive = new HashMap<>();
        int countContainingShinyGold = 0;
        for (String colour : bagRules.keySet()) {
            calculateFor(colour, bagRules, bagRulesRecursive);
            if (bagRulesRecursive.get(colour).contains("shiny gold"))
                countContainingShinyGold++;
        }
    
        System.out.printf("Colours which can contain 'shiny gold': %d%n", countContainingShinyGold);
    }
    
    /**
     * Recursive method to calculate *all* the containing bags for the specified colour.
     * When calculated, the containing colours set is placed into the bagRulesRecursive map.
     */
    private static void calculateFor(String name, Map<String, Set<String>> directBagRules,
                                        Map<String, Set<String>> bagRulesRecursive) {
        if (bagRulesRecursive.containsKey(name)) return;
    
        Set<String> directlyContained = directBagRules.get(name);
        if (directlyContained != null) {
            Set<String> contains = new HashSet<>(directlyContained);   // Copy direct containing colours
            for (String contColour : directlyContained) {              // For each directly contained colour...
                calculateFor(contColour, directBagRules, bagRulesRecursive); // Recursively calculate
                contains.addAll(bagRulesRecursive.get(contColour));    // Add all calculated values
            }
            bagRulesRecursive.put(name, contains);
        } else {
            // Bag doesn't contain any other bags, store an empty set
            bagRulesRecursive.put(name, Collections.emptySet());
        }
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
