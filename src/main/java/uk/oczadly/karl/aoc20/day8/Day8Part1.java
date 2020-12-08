package uk.oczadly.karl.aoc20.day8;

import uk.oczadly.karl.aoc20.Helper;

import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Karl Oczadly
 */
public class Day8Part1 {
    
    public static void main(String[] args) {
        Computer comp = Computer.load(Helper.streamInput(8));
        System.out.printf("Loaded %,d program instructions...%n", comp.instructions.size());
        
        boolean[] executed = new boolean[comp.instructions.size()]; // Tracks what instructions have been executed
        int prevInstrIndex = 0, prevAccumulator = 0;
        
        do {
            if (comp.instrIndex >= comp.instructions.size()) break;
            if (executed[comp.instrIndex]) {
                // Already executed, found solution!
                System.out.printf("Instruction %d caused an infinite loop.%nFinal accumulator value = %d%n",
                        prevInstrIndex, prevAccumulator);
                break;
            }
            executed[comp.instrIndex] = true;
            prevInstrIndex = comp.instrIndex;
            prevAccumulator = comp.accumulator;
        } while (comp.execute());
    }
    
    
    static class Computer {
        int instrIndex, accumulator;
        final List<Instruction> instructions;
        
        public Computer(List<Instruction> instructions) {
            this.instructions = Collections.unmodifiableList(instructions);
        }
    
        /** Executes the next instruction. Returns false if the end of the program has been reached. */
        public boolean execute() {
            Instruction instr = getNextInstruction();
            if (instr == null) return false; // End of program
            instr.execute(this);
            instrIndex++; // Step to next instruction
            return true;
        }
        
        /** Returns the next instruction, or null if the end of the program has been reached. */
        public Instruction getNextInstruction() {
            if (instrIndex < 0) throw new IllegalStateException("Invalid instruction index.");
            if (instrIndex >= instructions.size()) return null; // End of program
            return instructions.get(instrIndex);
        }
        
        public static Computer load(Stream<String> instructions) {
            return new Computer(instructions
                    .map(Instruction::parse)
                    .collect(Collectors.toList()));
        }
    }
    
    /** Not really necessary, could just use split instead. */
    static final Pattern INSTRUCTION_PATTERN = Pattern.compile("^(\\w+) ([+\\-]\\d+)$");
    
    static class Instruction {
        final Operation op;
        final int num;
    
        Instruction(Operation op, int num) {
            this.op = op;
            this.num = num;
        }
        
        public void execute(Computer c) {
            op.execution.accept(c, num);
        }
        
        public static Instruction parse(String instr) {
            Matcher m = INSTRUCTION_PATTERN.matcher(instr);
            if (!m.matches()) throw new IllegalArgumentException("Invalid instruction format");
            return new Instruction(Operation.fromOpCode(m.group(1)), Integer.parseInt(m.group(2)));
        }
    }
    
    enum Operation {
        NO_OP       ("nop", (c, i) -> {}),
        JUMP        ("jmp", (c, i) -> c.instrIndex += (i - 1)), // Minus 1 to negate the default index increment
        ACCUMULATOR ("acc", (c, i) -> c.accumulator += i);
        
        
        final String opcode;
        final BiConsumer<Computer, Integer> execution;
        Operation(String opcode, BiConsumer<Computer, Integer> execution) {
            this.opcode = opcode;
            this.execution = execution;
        }
    
        public static Operation fromOpCode(String code) {
            for (Operation op : Operation.values())
                if (op.opcode.equalsIgnoreCase(code)) return op;
            throw new IllegalArgumentException("Unknown operation.");
        }
    }
    
}
