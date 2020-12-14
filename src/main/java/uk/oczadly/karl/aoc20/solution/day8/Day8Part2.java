package uk.oczadly.karl.aoc20.solution.day8;

import uk.oczadly.karl.aoc20.input.NoValidSolutionException;
import uk.oczadly.karl.aoc20.PuzzleSolution;
import uk.oczadly.karl.aoc20.input.IllegalInputException;
import uk.oczadly.karl.aoc20.input.PuzzleInput;
import uk.oczadly.karl.aoc20.util.EnumIndex;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Karl Oczadly
 */
public class Day8Part2 extends PuzzleSolution {
    
    public Day8Part2() {
        super(8, 2); // Initializes the day and part number
    }
    
    @Override
    public Object solve(PuzzleInput input) {
        // Load virtual machine image
        VirtualMachine vm = new VirtualMachine(input.asList(Instruction::parse));
        
        // Try modifying each instruction, one by one
        Solver solver = new Solver(vm);
        
        for (Instruction instr : vm.instructions) {
            Operation op = instr.op;
            if (op == Operation.JUMP || op == Operation.NO_OP) {
                instr.op = op == Operation.JUMP ? Operation.NO_OP : Operation.JUMP; // Swap operation
                if (solver.doesExecute()) {
                    // Found a working solution
                    return vm.accumulator;
                }
                instr.op = op; // Didn't fix - return op to previous
            }
        }
        
        throw new NoValidSolutionException();
    }
    
    
    /**
     * Helper class to test whether the VM executes it's loaded program.
     * This class is used so the 'executed' array can be re-used between attempts, improving performance.
     */
    static class Solver {
        final VirtualMachine vm;
        final boolean[] executed;
    
        public Solver(VirtualMachine vm) {
            this.vm = vm;
            this.executed = new boolean[vm.instructions.size()];
        }
    
        /** Returns true if the program loaded into the VM executes successfully. */
        public boolean doesExecute() {
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
            if (!m.matches()) throw new IllegalInputException("Invalid instruction format");
            return new Instruction(
                    Operation.INDEX_OPCODE.valueOf(m.group(1)),
                    Integer.parseInt(m.group(2)));
        }
    }
    
    enum Operation {
        NO_OP       ("nop", (vm, i) -> {}),
        JUMP        ("jmp", (vm, i) -> vm.instrIndex += (i - 1)), // Minus 1 to negate the default index increment
        ACCUMULATOR ("acc", (vm, i) -> vm.accumulator += i);
    
    
        public static final EnumIndex<Operation, String> INDEX_OPCODE =
                new EnumIndex<>(Operation.class, e -> e.opcode);
        
        final String opcode;
        final BiConsumer<VirtualMachine, Integer> execution;
        Operation(String opcode, BiConsumer<VirtualMachine, Integer> execution) {
            this.opcode = opcode;
            this.execution = execution;
        }
    }
    
}
