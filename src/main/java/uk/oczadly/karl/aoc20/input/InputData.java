package uk.oczadly.karl.aoc20.input;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Karl Oczadly
 */
public final class InputData {
    
    private final InputStream is;
    
    public InputData(InputStream is) {
        this.is = is;
    }
    
    
    /**
     * Streams a set of input data from the resources folder.
     * <p>The input data can only be read once, after it will be exhausted.</p>
     * @return a stream of each line in the file
     */
    public Stream<String> asStream() {
        return new BufferedReader(new InputStreamReader(is)).lines();
    }
    
    /**
     * Loads a set of input data from the resources folder into a {@link List}.
     * <p>The input data can only be read once, after it will be exhausted.</p>
     * @param mapper a function to convert the string into the desired type
     * @return a list of each line in the file
     */
    public <T> List<T> asList(Function<String, T> mapper) {
        return asStream().map(mapper).collect(Collectors.toList());
    }
    
    /**
     * Loads a set of input data from the resources folder into a {@link List}.
     * <p>The input data can only be read once, after it will be exhausted.</p>
     * @return a list of each line in the file
     */
    public List<String> asList() {
        return asStream().collect(Collectors.toList());
    }
    
    /**
     * Loads the input data as a single string.
     * <p>The input data can only be read once, after it will be exhausted.</p>
     * @return a String representing the input data
     */
    public String asString() {
        return String.join("\n", asList());
    }
    
    
    public void closeStream() throws IOException {
        is.close();
    }
    
}
