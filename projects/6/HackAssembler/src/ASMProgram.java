import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents an ASMProgram using preprocessing and translating provided code from string to binary format
 */

public class ASMProgram {
    private final List<Instruction> asmInstructions;

    public ASMProgram(List<String> asmLines) {
        ASMPreprocessor preprocessor = new ASMPreprocessor();
        List<String> asmLinesPreprocessed = preprocessor.process(asmLines);
        asmInstructions = ASMParser.parse(asmLinesPreprocessed);
    }

    /**
     * Returns the binary format representation of this ASMProgram
     * @return the binary format representation of this ASMProgram
     */
    public List<String> toBinary() {
        return asmInstructions.stream()
                .map(Instruction::toBinary)
                .collect(Collectors.toList());
    }
}
