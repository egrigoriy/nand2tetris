import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a ASMPreprocessor handler that removes empty spaces.
 */
public class RemoveEmptySpacesHandler implements ASMPreprocessorHandler {
    @Override
    public List<String> handle(List<String> input) {
        return input.stream()
                .map(el -> el.replaceAll("\\s+", ""))
                .collect(Collectors.toList());
    }
}
