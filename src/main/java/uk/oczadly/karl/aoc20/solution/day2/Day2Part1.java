package uk.oczadly.karl.aoc20.solution.day2;

import uk.oczadly.karl.aoc20.input.IllegalInputException;
import uk.oczadly.karl.aoc20.input.PuzzleInput;
import uk.oczadly.karl.aoc20.PuzzleSolution;
import uk.oczadly.karl.aoc20.util.InputUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Karl Oczadly
 */
public class Day2Part1 extends PuzzleSolution {
    
    public Day2Part1() { super(2, 1); } // Initializes the day and part number
    
    @Override
    public Object solve(PuzzleInput input) {
        return input.asStream()
                .map(PasswordEntry::parse)
                .filter(PasswordEntry::isValid)
                .count();
    }
    
    
    static class PasswordEntry {
        static final Pattern INPUT_MATCHER = Pattern.compile("^(\\d+)-(\\d+) (\\w): (\\w+)$"); // 8-9 x: xxxxxxxrk
        
        final String password;
        char policyChar;
        int policyMin, policyMax;
        
        public PasswordEntry(String password, char policyChar, int policyMin, int policyMax) {
            this.password = password;
            this.policyChar = policyChar;
            this.policyMin = policyMin;
            this.policyMax = policyMax;
        }
        
        /** Returns true if the password meets the criteria set by the password policy. */
        public boolean isValid() {
            int count = (int)password.chars().filter(c -> c == this.policyChar).count();
            return count >= policyMin && count <= policyMax;
        }
        
        /** Parses a password entry from the raw input data. */
        public static PasswordEntry parse(String s) {
            Matcher matcher = INPUT_MATCHER.matcher(s);
            if (!matcher.matches()) throw new IllegalInputException("Invalid input");
            return new PasswordEntry(matcher.group(4), matcher.group(3).charAt(0),
                            Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)));
        }
    }

}
