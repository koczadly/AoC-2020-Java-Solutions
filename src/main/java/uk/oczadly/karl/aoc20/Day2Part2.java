package uk.oczadly.karl.aoc20;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Karl Oczadly
 */
public class Day2Part2 {
    
    public static void main(String[] args) throws Exception {
        int valid = 0;
        for (String line : Helper.readInputs("2")) {
            if (PasswordEntry.parse(line).isValid())
                valid++;
        }
        System.out.printf("Valid password count: %d%n", valid);
    }
    
    
    static class PasswordPolicy {
        char c;
        int index1, index2;
    
        public PasswordPolicy(char c, int index1, int index2) {
            this.c = c;
            this.index1 = index1;
            this.index2 = index2;
        }
        
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
        
        public boolean isValid() {
            return policy.matches(password);
        }
        
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
