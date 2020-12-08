package uk.oczadly.karl.aoc20.day8;

import uk.oczadly.karl.aoc20.Helper;

import java.util.List;

/**
 * Simplified version of Day8Part1
 */
public class Day8Part1Simple {
    
    public static void main(String[] args) {
        List<String> input = Helper.loadInput(8);
        
        int inst = 0, acc = 0;
        boolean[] seen = new boolean[input.size()];
        while (true) {
            if (seen[inst]) {
                System.out.printf("Result = %d%n", acc);
                return;
            }
            seen[inst] = true;
            String s = input.get(inst);
            String op = s.split(" ")[0];
            int val = Integer.parseInt(s.split(" ")[1]);
            switch (op) {
                case "acc": acc += val; break;
                case "jmp": inst += val - 1; break;
            }
            inst++;
        }
    }
    
}
