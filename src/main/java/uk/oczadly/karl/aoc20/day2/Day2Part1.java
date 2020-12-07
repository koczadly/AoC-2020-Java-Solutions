package uk.oczadly.karl.aoc20.day2;

import uk.oczadly.karl.aoc20.Helper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Karl Oczadly
 */
public class Day2Part1 {
    
    public static void main(String[] args) throws Exception {
        long count = Helper.streamInput(2)
                .map(PasswordEntry::parse)
                .filter(PasswordEntry::isValid)
                .count();
        System.out.printf("Valid password count: %d%n", count);
    }
    
    
    static class PasswordPolicy {
        char c;
        int min, max;
    
        public PasswordPolicy(char c, int min, int max) {
            this.c = c;
            this.min = min;
            this.max = max;
        }
    
        /** Returns true if the given password meets the criteria set by this password policy. */
        public boolean matches(String s) {
            int count = 0;
            for (char c : s.toCharArray())
                if (c == this.c) count++;
            return count >= min && count <= max;
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
            if (!matcher.matches()) throw new IllegalArgumentException("Invalid input");
            
            return new PasswordEntry(matcher.group(4),
                    new PasswordPolicy(matcher.group(3).charAt(0),
                            Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)))
            );
        }
    }

}
