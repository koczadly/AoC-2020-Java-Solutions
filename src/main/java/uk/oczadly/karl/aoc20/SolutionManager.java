package uk.oczadly.karl.aoc20;

import java.util.*;

/**
 * @author Karl Oczadly
 */
public class SolutionManager {

    private final Map<Integer, Set<PuzzleSolution>> solutions = new HashMap<>();
    
    
    public void registerSolution(PuzzleSolution solution) {
        Set<PuzzleSolution> set = solutions.computeIfAbsent(solution.getDay(), HashSet::new);
        // Check if already contains
        if (set.contains(solution))
            throw new IllegalArgumentException("Solution is already registered to this manager.");
        set.add(solution);
    }
    
    public Set<PuzzleSolution> getSolutions(int day) {
        return solutions.getOrDefault(day, Collections.emptySet());
    }
    
    
    public static SolutionManager of(PuzzleSolution...solutions) {
        SolutionManager mgr = new SolutionManager();
        Arrays.stream(solutions).forEach(mgr::registerSolution);
        return mgr;
    }

}
