package uk.oczadly.karl.aoc20;

import uk.oczadly.karl.aoc20.input.PuzzleInput;
import uk.oczadly.karl.aoc20.input.InputRetriever;
import uk.oczadly.karl.aoc20.input.ResourceInputRetriever;
import uk.oczadly.karl.aoc20.solution.day1.Day1Part1;
import uk.oczadly.karl.aoc20.solution.day1.Day1Part2;
import uk.oczadly.karl.aoc20.solution.day10.Day10Part1;
import uk.oczadly.karl.aoc20.solution.day10.Day10Part2;
import uk.oczadly.karl.aoc20.solution.day11.Day11Part1;
import uk.oczadly.karl.aoc20.solution.day11.Day11Part2;
import uk.oczadly.karl.aoc20.solution.day12.Day12Part1;
import uk.oczadly.karl.aoc20.solution.day12.Day12Part2;
import uk.oczadly.karl.aoc20.solution.day13.Day13Part1;
import uk.oczadly.karl.aoc20.solution.day13.Day13Part2;
import uk.oczadly.karl.aoc20.solution.day2.Day2Part1;
import uk.oczadly.karl.aoc20.solution.day2.Day2Part2;
import uk.oczadly.karl.aoc20.solution.day3.Day3Part1;
import uk.oczadly.karl.aoc20.solution.day3.Day3Part2;
import uk.oczadly.karl.aoc20.solution.day4.Day4Part1;
import uk.oczadly.karl.aoc20.solution.day4.Day4Part2;
import uk.oczadly.karl.aoc20.solution.day5.Day5Part1;
import uk.oczadly.karl.aoc20.solution.day5.Day5Part2;
import uk.oczadly.karl.aoc20.solution.day6.Day6Part1;
import uk.oczadly.karl.aoc20.solution.day6.Day6Part2;
import uk.oczadly.karl.aoc20.solution.day7.Day7Part1;
import uk.oczadly.karl.aoc20.solution.day7.Day7Part2;
import uk.oczadly.karl.aoc20.solution.day8.Day8Part1;
import uk.oczadly.karl.aoc20.solution.day8.Day8Part1Simple;
import uk.oczadly.karl.aoc20.solution.day8.Day8Part2;
import uk.oczadly.karl.aoc20.solution.day9.Day9Part1;
import uk.oczadly.karl.aoc20.solution.day9.Day9Part2;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Karl Oczadly
 */
public class Main {
    
    /** The input retriever which loads the input data. */
    static final InputRetriever INPUT_RETRIEVER = new ResourceInputRetriever("inputs");
    
    /** A set of implemented solutions. */
    static final SolutionManager SOLUTIONS = SolutionManager.of(
            new Day1Part1(), new Day1Part2(),
            new Day2Part1(), new Day2Part2(),
            new Day3Part1(), new Day3Part2(),
            new Day4Part1(), new Day4Part2(),
            new Day5Part1(), new Day5Part2(),
            new Day6Part1(), new Day6Part2(),
            new Day7Part1(), new Day7Part2(),
            new Day8Part1(), new Day8Part1Simple(), new Day8Part2(),
            new Day9Part1(), new Day9Part2(),
            new Day10Part1(), new Day10Part2(),
            new Day11Part1(), new Day11Part2(),
            new Day12Part1(), new Day12Part2(),
            new Day13Part1());
    
    
    static final Comparator<PuzzleSolution> PROCESS_ORDER = Comparator.comparing(PuzzleSolution::getPart)
            .thenComparing(PuzzleSolution::getRevision);
    
    // Arguments: [day] [part] [version]
    public static void main(String[] args) {
        if (args.length == 0) {
            // Run all puzzle solutions
            for (int day = 1; day <= 25; day++)
                process(day, false);
        } else if (args.length == 1) {
            // Run for day
            process(Integer.parseInt(args[0]), true);
        } else if (args.length == 2) {
            // Run for day and part
            process(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        } else if (args.length == 3) {
            // Run for day, part and revision
            process(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]));
        }
    }
    
    
    private static void process(int day, boolean printError) {
        List<PuzzleSolution> solutions = SOLUTIONS.getSolutions(day).stream()
                .sorted(PROCESS_ORDER)
                .collect(Collectors.toList());
        if (!solutions.isEmpty()) {
            solutions.forEach(Main::run);
        } else if (printError) {
            System.err.println("No solution found for the specified day.");
        }
    }
    
    private static void process(int day, int part) {
        List<PuzzleSolution> solutions = SOLUTIONS.getSolutions(day).stream()
                .filter(s -> s.getPart() == part)
                .sorted(PROCESS_ORDER)
                .collect(Collectors.toList());
        if (!solutions.isEmpty()) {
            solutions.forEach(Main::run);
        } else {
            System.err.println("No solution found for the specified day/part.");
        }
    }
    
    private static void process(int day, int part, int version) {
        Optional<PuzzleSolution> solution = SOLUTIONS.getSolutions(day).stream()
                .filter(s -> s.getPart() == part && s.getRevision() == version)
                .findAny();
        if (solution.isPresent()) {
            run(solution.get());
        } else {
            System.err.println("No solution found for the specified day/part/version.");
        }
    }
    
    
    private static void run(PuzzleSolution sol) {
        try {
            PuzzleInput input = INPUT_RETRIEVER.fetchInput(sol.getDay());
            // Run solution
            try {
                long startTime = System.nanoTime();
                Object result = sol.solve(input);
                double timeTaken = (System.nanoTime() - startTime) / 1e9;
                if (result != null) {
                    System.out.printf("[%.3fs] Day %d Part %d (v%d) = %s%n",
                            timeTaken, sol.getDay(), sol.getPart(), sol.getRevision(), result);
                } else {
                    printError(sol, "Result was null");
                }
            } catch (NoSolutionFoundException e) {
                printError(sol, e.getMessage());
            } catch (Throwable t) {
                t.printStackTrace();
                printError(sol, "Exception occurred when processing");
            } finally {
                input.closeStream();
            }
        } catch (Exception e) {
            e.printStackTrace();
            printError(sol, "Unable to retrieve input data");
        }
    }
    
    private static void printError(PuzzleSolution sol, String msg) {
        System.err.printf("Day %d Part %d (v%d): %s%n",
                sol.getDay(), sol.getPart(), sol.getRevision(), msg);
    }
    
}
