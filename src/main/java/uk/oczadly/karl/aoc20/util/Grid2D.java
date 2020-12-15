package uk.oczadly.karl.aoc20.util;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Represents a 2-dimensional grid of objects.
 */
public class Grid2D<T> {

    private final int width, height;
    private final Object[][] table; // [y/row][x/col]
    
    public Grid2D(int width, int height) {
        this(width, height, null);
    }
    
    public Grid2D(int width, int height, T initialVal) {
        this.width = width;
        this.height = height;
        this.table = new Object[height][width];
        // Fill the array
        if (initialVal != null) {
            for (int y = 0; y < height; y++) {
                Arrays.fill(table[y], initialVal);
            }
        }
    }
    
    private Grid2D(Object[][] table, int width, int height) {
        this.width = width;
        this.height = height;
        this.table = table;
    }
    
    
    public int getWidth() {
        return width;
    }
    
    public int getHeight() {
        return height;
    }
    
    public T get(int x, int y) {
        if (x < 0 || x >= width) throw new IndexOutOfBoundsException("Invalid X index.");
        if (y < 0 || y >= height) throw new IndexOutOfBoundsException("Invalid Y index.");
        return (T)table[y][x];
    }
    
    public T getOutOfBounds(int x, int y) {
        return getOutOfBounds(x, y, null);
    }
    
    public T getOutOfBounds(int x, int y, T defaultVal) {
        if (!isInRange(x, y))
            return defaultVal;
        return (T)table[y][x];
    }
    
    public void set(int x, int y, T value) {
        if (x < 0 || x >= width) throw new IndexOutOfBoundsException("Invalid X index.");
        if (y < 0 || y >= height) throw new IndexOutOfBoundsException("Invalid Y index.");
        table[y][x] = value;
    }
    
    public boolean isInRange(int x, int y) {
        return x >= 0 || x < width || y >= 0 || y < height;
    }
    
    public Stream<T> streamElements() {
        return Arrays.stream(table)
                .flatMap(Arrays::stream)
                .map(o -> (T)o);
    }
    
    
    public static Grid2D<Character> fromLineChars(List<String> input) {
        return fromLineChars(input, Function.identity());
    }
    
    public static <T> Grid2D<T> fromLineChars(List<String> input, Function<Character, T> mapper) {
        int width = input.isEmpty() ? 0 : input.get(0).length(), height = input.size();
        Object[][] table = new Object[height][width];
        // Load into array
        for (int y = 0; y < height; y++) {
            String line = input.get(y);
            if (line.length() != width)
                throw new IllegalArgumentException("Line widths do not match.");
            for (int x = 0; x < width; x++)
                table[y][x] = mapper.apply(line.charAt(x));
        }
        return new Grid2D<>(table, width, height);
    }
    
    public static <T> Grid2D<T> fromList(List<List<T>> list) {
        int width = list.isEmpty() ? 0 : list.get(0).size(), height = list.size();
        Object[][] table = new Object[height][width];
        // Load into array
        for (int y = 0; y < height; y++) {
            List<T> line = list.get(y);
            if (line.size() != width)
                throw new IllegalArgumentException("Line widths do not match.");
            for (int x = 0; x < width; x++)
                table[y][x] = line.get(x);
        }
        return new Grid2D<>(table, width, height);
    }
    
    public static <T> Grid2D<T> copyOf(Grid2D<T> grid) {
        Object[][] table = new Object[grid.height][grid.width];
        for (int i = 0; i < grid.height; i++) {
            System.arraycopy(grid.table[i], 0, table[i], 0, grid.table[i].length);
        }
        return new Grid2D<>(table, grid.width, grid.height);
    }
    
}
