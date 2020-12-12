package uk.oczadly.karl.aoc20.util;

import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Karl Oczadly
 */
public class InputUtil {
    
    public static Function<String, Matcher> mapRegex(Pattern p) {
        return s -> {
            Matcher m = p.matcher(s);
            if (!m.matches())
                throw new IllegalArgumentException("Pattern did not match input data.");
            return m;
        };
    }

}
