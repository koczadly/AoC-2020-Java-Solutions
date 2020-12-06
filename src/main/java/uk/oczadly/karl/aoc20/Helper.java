package uk.oczadly.karl.aoc20;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * @author Karl Oczadly
 */
public class Helper {
    
    public static <T> List<T> readInputs(String dayName, Function<String, T> parseFunc) throws Exception {
        InputStream fis = Helper.class.getClassLoader().getResourceAsStream("day" + dayName + ".txt");
        if (fis == null) throw new FileNotFoundException("Resource input not found.");
        BufferedReader input = new BufferedReader(new InputStreamReader(fis));
        
        List<T> vals = new ArrayList<>();
        String line;
        while ((line = input.readLine()) != null) {
            vals.add(parseFunc.apply(line));
        }
        return vals;
    }
    
    public static List<String> readInputs(String dayName) throws Exception {
        return readInputs(dayName, s -> s);
    }
    
}
