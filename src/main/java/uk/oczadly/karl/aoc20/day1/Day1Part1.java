package uk.oczadly.karl.aoc20.day1;

import uk.oczadly.karl.aoc20.Helper;

import java.util.List;

/**
 * @author Karl Oczadly
 */
public class Day1Part1 {
    
    public static void main(String[] args) throws Exception {
        List<Integer> ints = Helper.readInputs("1", Integer::valueOf);
        loop: for (int i : ints) {
            for (int j : ints) {
                if (i != j && i + j == 2020) {
                    System.out.printf("%d + %d = 2020, %1$d * %2$d = %d%n",
                            i, j, i * j);
                            break loop;
                }
            }
        }
    }

}
