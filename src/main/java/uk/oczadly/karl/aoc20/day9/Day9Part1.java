package uk.oczadly.karl.aoc20.day9;

import uk.oczadly.karl.aoc20.Helper;

import java.util.List;

/**
 * @author Karl Oczadly
 */
public class Day9Part1 {
    
    public static void main(String[] args) {
        List<Long> input = Helper.loadInput(9, Long::parseLong);
        
        for (int i = 25; i < input.size(); i++) {
            long num = input.get(i);
            boolean isValid = false;
            validCheck: for (int j = i - 25; j < i; j++) {
                for (int k = i - 25; k < i; k++) {
                    if (j != k && input.get(j) + input.get(k) == num) {
                        isValid = true;
                        break validCheck;
                    }
                }
            }
            if (!isValid) {
                System.out.printf("First invalid number = %d%n", num);
                return;
            }
        }
    }
    
}
