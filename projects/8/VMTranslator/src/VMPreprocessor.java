import java.util.List;
import java.util.stream.Collectors;

class VMPreprocessor {
    public static List<String> process(List<String> lines) {
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