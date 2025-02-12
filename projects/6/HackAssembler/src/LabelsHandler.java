import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Represents a ASMPreprocessor handler that handles labels.
 */
public class LabelsHandler implements ASMPreprocessorHandler {
    SymbolTable symbolTable = new SymbolTable();

    /**
     * Handles labels in a given machine language program in 3 steps:
     * 1. Put all labels in a Symbol Table
     * 2. Remove all label pseudo-instructions
     * 3. Replace label references with corresponding addresses
     *
     * @param input
     * @return program with addresses instead of labels
     */
    @Override
    public List<String> handle(List<String> input) {
        // put labels in Symbol table
        putLabelsInSymbolTable(input);
        // remove label pseudo-instructions
        List<String> noLabelInstructions = removeLabelPseudoInstructions(input);
        // replace label references with address
        return replaceLabelReferencesWithAddress(noLabelInstructions);
    }

    /**
     * Puts all labels from labels pseudo-instructions contained in the input into this symbol table
     * @param input
     */
    private void putLabelsInSymbolTable(List<String> input) {
        Pattern pattern = Pattern.compile("\\(([^()]*)\\)");
        int pc = 0;
        for (String instruction : input) {
            Matcher matcher = pattern.matcher(instruction);
            // find labels
            if (matcher.find()) {
                // if found put in SymbolTable
                String symbol = matcher.group(1);
                symbolTable.addEntry(symbol, pc);
            } else {
                // skip line
                pc++;
            }
        }
    }

    /**
     * Removes label pseudo-instruction from the given input
     * @param input
     * @return input with removed label pseudo-instructions
     */
    private List<String> removeLabelPseudoInstructions(List<String> input) {
        Pattern pattern = Pattern.compile("\\(([^()]*)\\)");
        return input.stream()
                .filter(el -> {
                    Matcher matcher = pattern.matcher(el);
                    return !matcher.find();
                })
                .collect(Collectors.toList());
    }

    /**
     * Replaces all label references with corresponding addresses
     * @param input
     * @return all label references replaced with corresponding addresses
     */
    private List<String> replaceLabelReferencesWithAddress(List<String> input) {
        List<String> result = new ArrayList<>();
        for (String instruction : input) {
            if (isAInstructionWithSymbol(instruction)) {
                instruction = replaceLabelReferenceWithAddress(instruction);
            }
            result.add(instruction);
        }
        return result;
    }

    /**
     * Replaces A-instruction symbol with corresponding address only if label
     * @param instruction
     * @return A-instruction with address corresponding to a label if such
     */
    private String replaceLabelReferenceWithAddress(String instruction) {
        String symbol = instruction.substring(1);
        if (symbolTable.contains(symbol)) {
            return AInstruction.ASM_PREFIX + symbolTable.getAddress(symbol);
        } else {
            return instruction;
        }
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
