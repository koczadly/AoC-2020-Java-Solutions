package uk.oczadly.karl.aoc20.solution.day2;

import uk.oczadly.karl.aoc20.input.IllegalInputException;
import uk.oczadly.karl.aoc20.input.PuzzleInput;
import uk.oczadly.karl.aoc20.PuzzleSolution;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Karl Oczadly
 */
public class Day2Part2 extends PuzzleSolution {
    
    public Day2Part2() {
        super(2, 2); // Initializes the day and part number
    }
    
    @Override
    public Object solve(PuzzleInput input) {
        return input.asStream()
                .map(PasswordEntry::parse)
                .filter(PasswordEntry::isValid)
                .count();
    }
    
    
    static class PasswordPolicy {
        char c;
        int index1, index2;
    
        public PasswordPolicy(char c, int index1, int index2) {
            this.c = c;
            this.index1 = index1;
            this.index2 = index2;
        }
    
        /** Returns true if the given password meets the criteria set by this password policy. */
        public boolean matches(String s) {
            return (s.length() >= index1 && s.charAt(index1 - 1) == c)
                    ^ (s.length() >= index2 && s.charAt(index2 - 1) == c);
        }
    }
    
    // 8-9 x: xxxxxxxrk
    static final Pattern INPUT_MATCHER = Pattern.compile("^(\\d+)-(\\d+) (\\w): (\\w+)$");
    
    static class PasswordEntry {
        String password;
        PasswordPolicy policy;
        
        public PasswordEntry(String password, PasswordPolicy policy) {
            this.password = password;
            this.policy = policy;
        }
    
        /** Returns true if the password meets the criteria set by the password policy. */
        public boolean isValid() {
            return policy.matches(password);
        }
    
        /** Parses a password entry from the raw input data. */
        public static PasswordEntry parse(String s) {
            Matcher matcher = INPUT_MATCHER.matcher(s);
            if (!matcher.matches()) throw new IllegalInputException("Invalid input");
            
            return new PasswordEntry(matcher.group(4),
                    new PasswordPolicy(matcher.group(3).charAt(0),
                            Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)))
            );
        }
    }

}
