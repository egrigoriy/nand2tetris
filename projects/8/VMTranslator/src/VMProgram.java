import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class VMProgram {
    public static List<String> toASM(Map<String, List<String>> vmFiles) {
        List<String> result = new ArrayList<>();
        for (String fileName : vmFiles.keySet()) {
            List<String> vmLines = vmFiles.get(fileName);
            result.addAll(VMParser.parse(fileName, vmLines));
        }
        if (vmFiles.keySet().size() == 1) {
            result.addAll(endInfiniteLoop());
        } else {
            result.addAll(0, bootstrap());
        }
        return result;
    }

    private static List<String> bootstrap() {
        List<String> result = new ArrayList<>(List.of(
                "@256",
                "D=A",
                "@SP",
                "M=D"
        ));
        result.addAll(VMParser.parse("", List.of("call Sys.init 0")));
        return result;
    }

    private static List<String> endInfiniteLoop() {
        return List.of(
                "(END)",
                "@END",
                "0;JMP"
        );
    }
}
