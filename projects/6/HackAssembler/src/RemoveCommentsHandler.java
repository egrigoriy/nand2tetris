import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a ASMPreprocessor handler that removes comments (line and end-of-line).
 */
public class RemoveCommentsHandler implements ASMPreprocessorHandler {
    @Override
    public List<String> handle(List<String> input) {
        return removeEndLineComments(removeFullLineComments(input));
    }

    /**
     * Removes the line comments from a given input list of strings
     * @param input
     * @return input with removed line comments
     */
    private List<String> removeFullLineComments(List<String> input) {
        return input.stream()
                .filter(el -> !el.trim().startsWith("//"))
                .collect(Collectors.toList());
    }

    /**
     * Removes the end-of-line comments from a given input list of strings
     * @param input
     * @return input with removed end-of-line comments
     */
    private List<String> removeEndLineComments(List<String> input) {
        return input.stream()
                .map(el -> el.contains("/") ? el.substring(0, el.indexOf("/")) : el)
                .map(String::trim)
                .collect(Collectors.toList());
    }
}
