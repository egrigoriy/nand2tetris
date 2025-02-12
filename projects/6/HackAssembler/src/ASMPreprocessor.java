import java.util.ArrayList;
import java.util.List;

/**
 * Represents an ASMPreprocessor that applies all its handlers to given input
 */
public class ASMPreprocessor {
    List<ASMPreprocessorHandler> handlers = new ArrayList<>();

    public ASMPreprocessor() {
        handlers.add(new RemoveEmptyLinesHandler());
        handlers.add(new RemoveEmptySpacesHandler());
        handlers.add(new RemoveCommentsHandler());
        handlers.add(new LabelsHandler());
        handlers.add(new VariablesHandler());
    }

    /**
     * Applies all handlers to the given input
     * @param input
     * @return preprocessed input
     */
    public List<String> process(List<String> input) {
        List<String> result = input;
        for (ASMPreprocessorHandler handler : handlers) {
            result = handler.handle(result);
        }
        return result;
    }
}
