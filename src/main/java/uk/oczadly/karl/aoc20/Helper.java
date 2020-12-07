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
    
    public static Stream<String> streamInput(String dayName) throws Exception {
        InputStream fis = Helper.class.getClassLoader().getResourceAsStream("inputs/day" + dayName + ".txt");
        if (fis == null) throw new FileNotFoundException("Input resource not found.");
        return new BufferedReader(new InputStreamReader(fis)).lines();
    }
    
    public static <T> List<T> loadInput(String dayName, Function<String, T> parseFunc) throws Exception {
        return streamInput(dayName).map(parseFunc).collect(Collectors.toList());
    }
    
    public static List<String> loadInput(String dayName) throws Exception {
        return loadInput(dayName, s -> s);
    }
    
}
