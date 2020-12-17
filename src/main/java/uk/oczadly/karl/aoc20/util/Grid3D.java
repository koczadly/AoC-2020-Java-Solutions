package uk.oczadly.karl.aoc20.util;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Represents a 3-dimensional grid of objects.
 */
public class Grid3D<T> {

    private final int xLen, yLen, zLen;
    private final Object[][][] table; // [z][y][x]
    
    public Grid3D(int xLen, int yLen, int zLen) {
        this(xLen, yLen, zLen, null);
    }
    
    public Grid3D(int xLen, int yLen, int zLen, T initialVal) {
        this(new Object[zLen][yLen][xLen], xLen, yLen, zLen);
        // Fill the array
        if (initialVal != null) {
            for (int z = 0; z < zLen; z++) {
                for (int y = 0; y < yLen; y++) {
                    Arrays.fill(table[z][y], initialVal);
                }
            }
        }
    }
    
    private Grid3D(Object[][][] table, int xLen, int yLen, int zLen) {
        this.xLen = xLen;
        this.yLen = yLen;
        this.zLen = zLen;
        this.table = table;
    }
    
    
    public int size() {
        return xLen * yLen * zLen;
    }
    
    public int getLengthX() {
        return xLen;
    }
    
    public int getLengthY() {
        return yLen;
    }
    
    public int getLengthZ() {
        return zLen;
    }
    
    public T get(int x, int y, int z) {
        if (x < 0 || x >= xLen) throw new IndexOutOfBoundsException("Invalid X index.");
        if (y < 0 || y >= yLen) throw new IndexOutOfBoundsException("Invalid Y index.");
        if (z < 0 || z >= zLen) throw new IndexOutOfBoundsException("Invalid Z index.");
        return (T)table[z][y][x];
    }
    
    public T getOutOfBounds(int x, int y, int z) {
        return getOutOfBounds(x, y, z, null);
    }
    
    public T getOutOfBounds(int x, int y, int z, T defaultVal) {
        if (!isInRange(x, y, z))
            return defaultVal;
        return (T)table[z][y][x];
    }
    
    public void set(int x, int y, int z, T value) {
        if (x < 0 || x >= xLen) throw new IndexOutOfBoundsException("Invalid X index.");
        if (y < 0 || y >= yLen) throw new IndexOutOfBoundsException("Invalid Y index.");
        if (z < 0 || z >= zLen) throw new IndexOutOfBoundsException("Invalid Z index.");
        table[z][y][x] = value;
    }
    
    public void setPlaneZ(int z, int xOff, int yOff, Grid2D<T> grid2d) {
        if (grid2d.getWidth() != xLen) throw new IllegalArgumentException("X length of 2D grid doesn't match.");
        if (grid2d.getHeight() != yLen) throw new IllegalArgumentException("Y length of 2D grid doesn't match.");
        if (xOff < 0 || xOff + grid2d.getWidth() > xLen) throw new IllegalArgumentException("Invalid X offset.");
        if (yOff < 0 || yOff + grid2d.getHeight() > yLen) throw new IllegalArgumentException("Invalid Y offset.");
        for (int y = yOff; y < grid2d.getHeight(); y++)
            for (int x = xOff; x < grid2d.getWidth(); x++)
                set(x, y, z, grid2d.get(x, y));
    }
    
    public boolean isInRange(int x, int y, int z) {
        return x >= 0 && x < xLen && y >= 0 && y < yLen && z >= 0 && z < zLen;
    }
    
    public Stream<T> streamElements() {
        return Arrays.stream(table)
                .flatMap(Arrays::stream)
                .flatMap(Arrays::stream)
                .map(o -> (T)o);
    }
    
    @Override
    public String toString() {
        return "Grid3D{" +
                "xLen=" + xLen +
                ", yLen=" + yLen +
                ", zLen=" + zLen + '}';
    }
    
    
    
    public static <T> Grid3D<T> copyOf(Grid3D<T> grid) {
        Object[][][] table = new Object[grid.zLen][grid.yLen][grid.xLen];
        for (int z = 0; z < grid.zLen; z++) {
            for (int y = 0; y < grid.yLen; y++) {
                System.arraycopy(grid.table[z][y], 0, table[z][y], 0, grid.table[z][y].length);
            }
        }
        return new Grid3D<>(table, grid.xLen, grid.yLen, grid.zLen);
    }
    
}
