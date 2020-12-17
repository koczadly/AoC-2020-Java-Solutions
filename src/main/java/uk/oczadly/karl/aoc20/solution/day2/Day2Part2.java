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
                .map(PasswordEntry::new)
                .filter(PasswordEntry::isValid)
                .count();
    }
    
    static class PasswordEntry {
        static final Pattern INPUT_MATCHER = Pattern.compile("^(\\d+)-(\\d+) (\\w): (\\w+)$"); // 8-9 x: xxxxxxxrk
    
        final String pswd;
        char policyChar;
        int policyIdx1, policyIdx2;
    
        /** Parses a password entry from the raw input data. */
        public PasswordEntry(String str) {
            Matcher matcher = INPUT_MATCHER.matcher(str);
            if (!matcher.matches()) throw new IllegalInputException("Invalid input");
            this.pswd = matcher.group(4);
            this.policyChar = matcher.group(3).charAt(0);
            this.policyIdx1 = Integer.parseInt(matcher.group(1));
            this.policyIdx2 = Integer.parseInt(matcher.group(2));
        }
    
        /** Returns true if the password meets the criteria set by the password policy. */
        public boolean isValid() {
            return (pswd.length() >= policyIdx1 && pswd.charAt(policyIdx1 - 1) == policyChar)
                    != (pswd.length() >= policyIdx2 && pswd.charAt(policyIdx2 - 1) == policyChar);
        }
    }

}
