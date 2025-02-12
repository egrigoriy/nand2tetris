import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a ASMPreprocessor handler that removes empty lines.
 */
public class RemoveEmptyLinesHandler implements ASMPreprocessorHandler {
    @Override
    public List<String> handle(List<String> input) {
        return input.stream()
                .filter(el -> !el.trim().isEmpty())
                .collect(Collectors.toList());
    }
}
