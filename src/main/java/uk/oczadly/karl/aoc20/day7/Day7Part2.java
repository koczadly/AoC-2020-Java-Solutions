package uk.oczadly.karl.aoc20.day7;

import uk.oczadly.karl.aoc20.Helper;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * @author Karl Oczadly
 */
public class Day7Part2 {
    
    static final Pattern RULE_PATTERN = Pattern.compile(
            "(?:(^\\w+ \\w+) bags contain )|(?:\\G(\\d+) (\\w+ \\w+) bags?(?:, |.))");
    
    
    public static void main(String[] args) {
        // Load bag rules in
        Map<String, Set<ContainingBagRule>> bagRules = loadBagRules();
    
        // Calculate bag counts
        int count = calculateBagCount("shiny gold", bagRules, new HashMap<>());
        
        System.out.printf("Bags held within 'shiny gold': %d%n", count);
    }
    
    
    /**
     * Recursive method to calculate the number of contained bags.
     * The 'cache' map is used to store already computed values, meaning they don't need to be re-calculated.
     */
    private static int calculateBagCount(String colour, Map<String, Set<ContainingBagRule>> bagRules,
                                         Map<String, Integer> cache) {
        if (cache.containsKey(colour))
            return cache.get(colour); // Use cached value if available
        
        int count = 0;
        if (bagRules.containsKey(colour)) {
            for (ContainingBagRule rule : bagRules.get(colour)) {
                count += (calculateBagCount(rule.colour, bagRules, cache) + 1) * rule.count;
            }
        }
        cache.put(colour, count);
        return count;
    }
    
    /** Loads all of the bag rules from the input data. */
    private static Map<String, Set<ContainingBagRule>> loadBagRules() {
        Map<String, Set<ContainingBagRule>> bagRules = new HashMap<>();
        Helper.streamInput(7)
                .map(RULE_PATTERN::matcher)
                .forEach(matcher -> {
                    String name = null;
                    Set<ContainingBagRule> contains = new HashSet<>();
                    while (matcher.find()) {
                        if (name == null) {
                            // First match is the colour of the bag
                            name = matcher.group(1).toLowerCase();
                        } else {
                            // Subsequent matches are what the bag can contain
                            contains.add(new ContainingBagRule(
                                    matcher.group(3).toLowerCase(), Integer.parseInt(matcher.group(2))));
                        }
                    }
                    bagRules.put(name, contains);
                });
        return bagRules;
    }
    
    
    static class ContainingBagRule {
        final String colour;
        final int count;
        
        public ContainingBagRule(String colour, int count) {
            this.colour = colour;
            this.count = count;
        }
    }
    
}
