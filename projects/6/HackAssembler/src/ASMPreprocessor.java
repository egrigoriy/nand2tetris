import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ASMPreprocessor {
    public void apply() {
    }

    public List<String> removeEmptyLines(List<String> input) {
        List<String> result = new ArrayList<>();
        result = input.stream().filter(el -> !el.trim().isEmpty())
                .collect(Collectors.toList());
        return result;
    }

    public List<String> removeEmptySpaces(List<String> input) {
        return input.stream()
                .map(el -> el.replaceAll("\\s+", ""))
                .collect(Collectors.toList());
    }

    public List<String> removeComments(List<String> input) {
        // remove full line comments
        List<String> result = input.stream()
                .filter(el -> !el.trim().startsWith("//"))
                .collect(Collectors.toList());
        // remove inline comments
        result = result.stream()
                .map(el -> el.contains("/") ? el.substring(0, el.indexOf("/")) : el)
                .map(String::trim)
                .collect(Collectors.toList());
        return result;
    }

    public void putLabelsInSymbolTable(List<String> input, SymbolTable st) {
        Pattern pattern = Pattern.compile("\\(([^()]*)\\)");
        int pc = 0;
        for (String instruction : input) {
            Matcher matcher = pattern.matcher(instruction);
            // find labels
            if (matcher.find()) {
                // put in SymbolTable
                String symbol = matcher.group(1);
                st.addEntry(symbol, pc);
            } else {
                // skip line
                pc++;
            }
        }
    }

    public List<String> removeLabelPseudoInstructions(List<String> input) {
        Pattern pattern = Pattern.compile("\\(([^()]*)\\)");
        return input.stream()
                .filter(el -> {
                    Matcher matcher = pattern.matcher(el);
                    return !matcher.find();
                })
                .collect(Collectors.toList());
    }

    public boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public List<String> replaceLabelReferencesWithAddress(List<String> input, SymbolTable st) {
        List<String> finalResult = new ArrayList<>();
        for (String instruction : input) {
            if (instruction.startsWith("@") && !isInteger(instruction.substring(1))) {
                String symbol = instruction.substring(1);
                if (st.contains(symbol)) {
                    finalResult.add("@" + st.getAddress(symbol));
                } else {
                    finalResult.add(instruction);
                }

            } else {
                finalResult.add(instruction);
            }
        }
        return finalResult;
    }
    public List<String> handleLabels(List<String> input, SymbolTable st) {
        // put labels in Symbol table
        putLabelsInSymbolTable(input, st);
        // remove label pseudo-instruction
        List<String> out1 = removeLabelPseudoInstructions(input);
        // replace label reference with address
        List<String> finalResult = replaceLabelReferencesWithAddress(out1, st);
        return finalResult;
    }

    public List<String> handleVariables(List<String> input, SymbolTable st) {
        int varCounter = 16;
        List<String> result = new ArrayList<>();
        for (String instruction : input) {
            if (instruction.startsWith("@") && !isInteger(instruction.substring(1))) {
                String symbol = instruction.substring(1);
                if (st.contains(symbol)) {
                    instruction ="@" + st.getAddress(symbol);
                } else {
                    instruction = "@" + varCounter;
                    st.addEntry(symbol, varCounter);
                    varCounter++;
                }
            }
            result.add(instruction);
        }
        return result;
    }

    public List<String> process(List<String> lines) {
        SymbolTable symbolTable = new SymbolTable();
        List<String> result = removeEmptyLines(lines);
        result = removeEmptySpaces(result);
        result = removeComments(result);
        result = handleLabels(result, symbolTable);
        result = handleVariables(result, symbolTable);
        return result;
    }
}
