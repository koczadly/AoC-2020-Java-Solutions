package uk.oczadly.karl.aoc20.util;

import uk.oczadly.karl.aoc20.input.IllegalInputException;

import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Karl Oczadly
 */
public class InputUtil {
    
    public static Function<String, Matcher> regexMapper(Pattern p) {
        return s -> {
            Matcher m = p.matcher(s);
            if (!m.matches())
                throw new IllegalInputException("Pattern did not match input data.");
            return m;
        };
    }
    
    public static Function<String, Matcher> regexMapper(String pattern) {
        return regexMapper(Pattern.compile(pattern));
    }

}
