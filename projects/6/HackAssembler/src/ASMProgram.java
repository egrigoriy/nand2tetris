import java.util.ArrayList;
import java.util.List;

public class ASMProgram {
    List<String> asmText ;
    List<Instruction> asmInstructions;

    public ASMProgram(List<String> lines) {
        ASMPreprocessor preprocessor = new ASMPreprocessor();
        asmText = preprocessor.process(lines);
        ASMParser parser = new ASMParser();
        asmInstructions = parser.parse(asmText);
    }

    public String toBinary() {
        String result = "";
        for (Instruction instruction : asmInstructions) {
            result = result + instruction.toBinary() + "\n";
        }
        return result;
    }
}
