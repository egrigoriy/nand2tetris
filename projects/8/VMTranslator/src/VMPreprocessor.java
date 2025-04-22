import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a VM preprocessor
 */
class VMPreprocessor {
    public static List<String> process(List<String> lines) {
        return removeComments(removeEmptyLines(lines));
    }

    /**
     * Removes empty lines from given lines
     *
     * @param lines
     * @return lines with no empty one
     */
    private static List<String> removeEmptyLines(List<String> lines) {
        return lines.stream()
                .filter(el -> !el.trim().isEmpty())
                .collect(Collectors.toList());
    }

    /**
     * Removes full-line and end-line comments and empty spaces between characters
     *
     * @param lines
     * @return
     */
    private static List<String> removeComments(List<String> lines) {
        return lines.stream()
                .filter(el -> !el.trim().startsWith("//"))
                .map(el -> el.contains("/") ? el.substring(0, el.indexOf("/")) : el)
                .map(String::trim)
                .collect(Collectors.toList());
    }
}