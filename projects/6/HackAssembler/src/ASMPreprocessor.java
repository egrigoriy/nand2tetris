import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ASMPreprocessor {
    private SymbolTable symbolTable = new SymbolTable();

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

    public List<String> handleLabels(List<String> input, SymbolTable st) {
        Pattern pattern = Pattern.compile("\\(([^()]*)\\)");
        int pc = 0;
        List<String> result = new ArrayList<>();
        for (String instruction : input) {
            Matcher matcher = pattern.matcher(instruction);
            if (matcher.find()) {
                String symbol =  matcher.group(1);
                st.addEntry(symbol, pc);
            } else {
                result.add(instruction);
                pc++;
            }
        }
        return result;
    }

    public List<String> handleVariables() {
        return null;
    }
}
