package uk.oczadly.karl.aoc20;

import java.util.List;

/**
 * @author Karl Oczadly
 */
public class Day1Part2 {
    
    public static void main(String[] args) throws Exception {
        List<Integer> ints = Helper.readInputs("day1", Integer::valueOf);
        loop: for (int i : ints) {
            for (int j : ints) {
                for (int k : ints) {
                    if (i != j && i != k && j != k && i + j + k == 2020) {
                        System.out.printf("%d + %d + %d = 2020, %1$d * %2$d * %3$d = %d%n",
                                i, j, k, i * j * k);
                        break loop;
                    }
                }
            }
        }
    }

}
