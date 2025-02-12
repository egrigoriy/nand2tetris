import java.util.ArrayList;
import java.util.List;

/**
 * Represents a ASMPreprocessor handler that handles variables.
 */
public class VariablesHandler implements ASMPreprocessorHandler {
    private final int VARIABLES_START_ADDRESS = 16;
    SymbolTable symbolTable = new SymbolTable();

    /**
     * Handles variables in a given machine language program by using a symbol table
     * @param input
     * @return program with variables replaced with corresponding addresses
     */
    @Override
    public List<String> handle(List<String> input) {
        int varCounter = VARIABLES_START_ADDRESS;
        List<String> result = new ArrayList<>();
        for (String instruction : input) {
            if (isAInstructionWithSymbol(instruction)) {
                String symbol = instruction.substring(1);
                if (symbolTable.contains(symbol)) {
                    instruction = AInstruction.ASM_PREFIX + symbolTable.getAddress(symbol);
                } else {
                    instruction = AInstruction.ASM_PREFIX + varCounter;
                    symbolTable.addEntry(symbol, varCounter);
                    varCounter++;
                }
            }
            result.add(instruction);
        }
        return result;
    }

    /**
     * Returns true if given instruction is an AInstruction with symbol, otherwise false
     * @param instruction
     * @return true if given instruction is an AInstruction with symbol, otherwise false
     */
    private boolean isAInstructionWithSymbol(String instruction) {
        return AInstruction.isSuch(instruction) && !isInteger(instruction.substring(1));
    }

    /**
     * Returns true if a given string is an integer, otherwise false
     * @param s
     * @return true if given string is an integer. otherwise false
     */
    private boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
