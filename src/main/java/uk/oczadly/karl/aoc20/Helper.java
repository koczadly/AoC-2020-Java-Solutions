package uk.oczadly.karl.aoc20;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * @author Karl Oczadly
 */
public class Helper {
    
    public static <T> List<T> readInputs(String name, Function<String, T> parseFunc) throws Exception {
        BufferedReader input = new BufferedReader(new InputStreamReader(
                Day1Part1.class.getClassLoader().getResourceAsStream(name + ".txt")));
        
        List<T> ints = new ArrayList<>();
        String line;
        while ((line = input.readLine()) != null) {
            ints.add(parseFunc.apply(line));
        }
        return ints;
    }
    
    public static List<String> readInputs(String name) throws Exception {
        return readInputs(name, String::valueOf);
    }
    
}
