package uk.oczadly.karl.aoc20;

import uk.oczadly.karl.aoc20.input.PuzzleInput;

import java.util.Objects;

/**
 * @author Karl Oczadly
 */
public abstract class PuzzleSolution {

    private final int day, part, revision;
    
    public PuzzleSolution(int day, int part, int revision) {
        if (day < 1 || day > 25) throw new IllegalArgumentException("Invalid day number.");
        if (part < 1) throw new IllegalArgumentException("Invalid part number.");
        if (revision < 1) throw new IllegalArgumentException("Invalid revision number.");
        this.day = day;
        this.part = part;
        this.revision = revision;
    }
    
    public PuzzleSolution(int day, int part) {
        this(day, part, 1);
    }
    
    
    public final int getDay() {
        return day;
    }
    
    public final int getPart() {
        return part;
    }
    
    public final int getRevision() {
        return revision;
    }
    
    /**
     * Solve the puzzle for the given input.
     * @param input the input data
     * @return the solution
     */
    public abstract Object solve(PuzzleInput input);
    
    
    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PuzzleSolution)) return false;
        PuzzleSolution that = (PuzzleSolution)o;
        return day == that.day &&
                part == that.part &&
                revision == that.revision;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(day, part, revision);
    }
    
}
