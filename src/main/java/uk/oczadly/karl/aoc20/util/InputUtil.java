package uk.oczadly.karl.aoc20.util;

import uk.oczadly.karl.aoc20.input.IllegalInputException;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    
    
    public static <T> List<T> split(String str, String regex, Function<String, T> mapper) {
        return splitStream(str, regex, mapper)
                .collect(Collectors.toList());
    }
    
    public static <T> Stream<T> splitStream(String str, String regex, Function<String, T> mapper) {
        return Arrays.stream(str.split(regex))
                .map(mapper);
    }

}
