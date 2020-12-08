package uk.oczadly.karl.aoc20.day8;

import uk.oczadly.karl.aoc20.Helper;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Karl Oczadly
 */
public class Day8Part2 {
    
    public static void main(String[] args) {
        VirtualMachine vm = VirtualMachine.loadImage(Helper.streamInput(8));
        System.out.printf("Loaded %,d program instructions...%n", vm.instructions.size());
    
        // Array is used for doesExecute - it's declared here so it can be reused for performance optimizations
        boolean[] executed = new boolean[vm.instructions.size()];
        
        // Try modifying each instruction, one by one
        for (Instruction instr : vm.instructions) {
            Operation op = instr.op;
            if (op == Operation.JUMP || op == Operation.NO_OP) {
                instr.op = op == Operation.JUMP ? Operation.NO_OP : Operation.JUMP; // Swap operation
                if (doesExecute(vm, executed)) {
                    // Found a working solution
                    System.out.printf("Found working program! Final accumulator value = %d%n",
                            vm.accumulator);
                    break;
                }
                instr.op = op; // Didn't fix - return op to previous
            }
        }
    }
    
    /** Returns true if the program loaded into the VM executes successfully. */
    public static boolean doesExecute(VirtualMachine vm, boolean[] executed) {
        Arrays.fill(executed, false); // Clear re-used array
        vm.reset(); // Reset the VM's state
        do {
            if (vm.instrIndex >= vm.instructions.size()) break;
            if (executed[vm.instrIndex])
                return false; // Infinite loop found
            executed[vm.instrIndex] = true;
        } while (vm.execute());
        // Return true if the program ended on the last instruction index + 1
        return vm.instrIndex == vm.instructions.size();
    }
    
    
    static class VirtualMachine {
        int instrIndex, accumulator;
        final List<Instruction> instructions;
        
        public VirtualMachine(List<Instruction> instructions) {
            this.instructions = instructions;
        }
    
        /** Executes the next instruction. Returns false if the end of the program has been reached. */
        public boolean execute() {
            Instruction instr = getNextInstruction();
            if (instr == null) return false; // End of program
            instr.execute(this);
            instrIndex++; // Step to next instruction
            return true;
        }
        
        public void reset() {
            this.instrIndex = 0;
            this.accumulator = 0;
        }
        
        /** Returns the next instruction, or null if the end of the program has been reached. */
        public Instruction getNextInstruction() {
            if (instrIndex < 0) throw new IllegalStateException("Invalid instruction index.");
            if (instrIndex >= instructions.size()) return null; // End of program
            return instructions.get(instrIndex);
        }
    
        /** Loads a VM image from a stream of raw string data. */
        public static VirtualMachine loadImage(Stream<String> instructions) {
            return new VirtualMachine(instructions
                    .map(Instruction::parse)
                    .collect(Collectors.toList()));
        }
    }
    
    /** Not really necessary, could just use split instead. */
    static final Pattern INSTRUCTION_PATTERN = Pattern.compile("^(\\w+) ([+\\-]\\d+)$");
    
    static class Instruction {
        Operation op;
        int num;
        
        Instruction(Operation op, int num) {
            this.op = op;
            this.num = num;
        }
        
        public void execute(VirtualMachine c) {
            op.execution.accept(c, num);
        }
        
        public static Instruction parse(String instr) {
            Matcher m = INSTRUCTION_PATTERN.matcher(instr);
            if (!m.matches()) throw new IllegalArgumentException("Invalid instruction format");
            return new Instruction(Operation.fromOpCode(m.group(1)), Integer.parseInt(m.group(2)));
        }
    }
    
    enum Operation {
        NO_OP       ("nop", (vm, i) -> {}),
        JUMP        ("jmp", (vm, i) -> vm.instrIndex += (i - 1)), // Minus 1 to negate the default index increment
        ACCUMULATOR ("acc", (vm, i) -> vm.accumulator += i);
        
        
        final String opcode;
        final BiConsumer<VirtualMachine, Integer> execution;
        Operation(String opcode, BiConsumer<VirtualMachine, Integer> execution) {
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
