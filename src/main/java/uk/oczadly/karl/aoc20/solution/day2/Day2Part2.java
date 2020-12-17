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
    
    public Day2Part2() { super(2, 2); } // Initializes the day and part number
    
    @Override
    public Object solve(PuzzleInput input) {
        return input.asStream()
                .map(PasswordEntry::parse)
                .filter(PasswordEntry::isValid)
                .count();
    }
    
    
    static class PasswordEntry {
        static final Pattern INPUT_MATCHER = Pattern.compile("^(\\d+)-(\\d+) (\\w): (\\w+)$"); // 8-9 x: xxxxxxxrk
    
        final String pass;
        char policyChar;
        int policyIdx1, policyIdx2;
        
        public PasswordEntry(String pass, char policyChar, int policyIdx1, int policyIdx2) {
            this.pass = pass;
            this.policyChar = policyChar;
            this.policyIdx1 = policyIdx1;
            this.policyIdx2 = policyIdx2;
        }
    
        /** Returns true if the password meets the criteria set by the password policy. */
        public boolean isValid() {
            return (pass.length() >= policyIdx1 && pass.charAt(policyIdx1 - 1) == policyChar)
                    ^ (pass.length() >= policyIdx2 && pass.charAt(policyIdx2 - 1) == policyChar);
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
