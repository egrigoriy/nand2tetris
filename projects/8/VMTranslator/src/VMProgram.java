import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class VMProgram {
    public static List<String> toASM(List<String> lines) {
        List<String> result = new ArrayList<>();
        result.addAll(VMParser.parse(preprocess(lines)));
        result.addAll(endInfiniteLoop());
        return result;
    }

    private static List<String> endInfiniteLoop() {
        return List.of(
                "(END)",
                "@END",
                "0;JMP"
        );
    }

    private static List<String> preprocess(List<String> lines) {
        return removeComments(removeEmptyLines(lines));
    }
    private static List<String> removeEmptyLines(List<String> lines) {
        return lines.stream()
                .filter(el -> !el.trim().isEmpty())
                .collect(Collectors.toList());
    }

    private static List<String> removeComments(List<String> lines) {
        return lines.stream()
                .filter(el -> !el.trim().startsWith("//"))
                .map(el -> el.contains("/") ? el.substring(0, el.indexOf("/")) : el)
                .map(String::trim)
                .collect(Collectors.toList());
    }
}
