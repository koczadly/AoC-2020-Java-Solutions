package uk.oczadly.karl.aoc20;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Karl Oczadly
 */
public class Helper {
    
    public static Stream<String> streamInput(int day) {
        InputStream fis = Helper.class.getClassLoader().getResourceAsStream("inputs/day" + day + ".txt");
        if (fis == null) throw new IllegalArgumentException("Input resource not found.");
        return new BufferedReader(new InputStreamReader(fis)).lines();
    }
    
    public static <T> List<T> loadInput(int day, Function<String, T> parseFunc) {
        return streamInput(day).map(parseFunc).collect(Collectors.toList());
    }
    
    public static List<String> loadInput(int day) {
        return loadInput(day, s -> s);
    }
    
}
