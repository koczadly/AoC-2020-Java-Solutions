package uk.oczadly.karl.aoc20.util;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * Represents a 4-dimensional grid of objects.
 */
// TODO: replace with n-dimensional implementation
public class Grid4D<T> {

    private final int xLen, yLen, zLen, wLen;
    private final Object[][][][] table; // [w][z][y][x]
    
    public Grid4D(int xLen, int yLen, int zLen, int wLen) {
        this(xLen, yLen, zLen, wLen, null);
    }
    
    public Grid4D(int xLen, int yLen, int zLen, int wLen, T initialVal) {
        this(new Object[wLen][zLen][yLen][xLen], xLen, yLen, zLen, wLen);
        // Fill the array
        if (initialVal != null) {
            for (int w = 0; w < zLen; w++) {
                for (int z = 0; z < zLen; z++) {
                    for (int y = 0; y < yLen; y++) {
                        Arrays.fill(table[w][z][y], initialVal);
                    }
                }
            }
        }
    }
    
    private Grid4D(Object[][][][] table, int xLen, int yLen, int zLen, int wLen) {
        this.xLen = xLen;
        this.yLen = yLen;
        this.zLen = zLen;
        this.wLen = wLen;
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
    
    public int getLengthW() {
        return wLen;
    }
    
    public T get(int x, int y, int z, int w) {
        if (x < 0 || x >= xLen) throw new IndexOutOfBoundsException("Invalid X index.");
        if (y < 0 || y >= yLen) throw new IndexOutOfBoundsException("Invalid Y index.");
        if (z < 0 || z >= zLen) throw new IndexOutOfBoundsException("Invalid Z index.");
        if (w < 0 || w >= wLen) throw new IndexOutOfBoundsException("Invalid W index.");
        return (T)table[w][z][y][x];
    }
    
    public T getOutOfBounds(int x, int y, int z, int w) {
        return getOutOfBounds(x, y, z, w, null);
    }
    
    public T getOutOfBounds(int x, int y, int z, int w, T defaultVal) {
        if (!isInRange(x, y, z, w))
            return defaultVal;
        return (T)table[w][z][y][x];
    }
    
    public void set(int x, int y, int z, int w, T value) {
        if (x < 0 || x >= xLen) throw new IndexOutOfBoundsException("Invalid X index.");
        if (y < 0 || y >= yLen) throw new IndexOutOfBoundsException("Invalid Y index.");
        if (z < 0 || z >= zLen) throw new IndexOutOfBoundsException("Invalid Z index.");
        if (w < 0 || w >= wLen) throw new IndexOutOfBoundsException("Invalid W index.");
        table[w][z][y][x] = value;
    }
    
    public void setPlaneZW(int z, int w, int xOff, int yOff, Grid2D<T> grid2d) {
        if (grid2d.getWidth() != xLen) throw new IllegalArgumentException("X length of 2D grid doesn't match.");
        if (grid2d.getHeight() != yLen) throw new IllegalArgumentException("Y length of 2D grid doesn't match.");
        if (xOff < 0 || xOff + grid2d.getWidth() > xLen) throw new IllegalArgumentException("Invalid X offset.");
        if (yOff < 0 || yOff + grid2d.getHeight() > yLen) throw new IllegalArgumentException("Invalid Y offset.");
        for (int y = yOff; y < grid2d.getHeight(); y++)
            for (int x = xOff; x < grid2d.getWidth(); x++)
                set(x, y, z, w, grid2d.get(x, y));
    }
    
    public boolean isInRange(int x, int y, int z, int w) {
        return x >= 0 && x < xLen && y >= 0 && y < yLen && z >= 0 && z < zLen && w >= 0 && w < wLen;
    }
    
    public Stream<T> streamElements() {
        return Arrays.stream(table)
                .flatMap(Arrays::stream)
                .flatMap(Arrays::stream)
                .flatMap(Arrays::stream)
                .map(o -> (T)o);
    }
    
    @Override
    public String toString() {
        return "Grid4D{" +
                "xLen=" + xLen +
                ", yLen=" + yLen +
                ", zLen=" + zLen +
                ", wLen=" + wLen + '}';
    }
    
    
    public static <T> Grid4D<T> copyOf(Grid4D<T> grid) {
        Object[][][][] table = new Object[grid.wLen][grid.zLen][grid.yLen][grid.xLen];
        for (int w = 0; w < grid.wLen; w++)
            for (int z = 0; z < grid.zLen; z++)
                for (int y = 0; y < grid.yLen; y++)
                    System.arraycopy(grid.table[w][z][y], 0, table[w][z][y], 0, grid.table[w][z][y].length);
        return new Grid4D<>(table, grid.xLen, grid.yLen, grid.zLen, grid.wLen);
    }
    
}
