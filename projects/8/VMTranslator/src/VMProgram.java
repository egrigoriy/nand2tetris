import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class VMProgram {
    public static List<String> toASM(Map<String, List<String>> vmFiles) {
        List<String> result = translateAll(vmFiles);
        if (vmFiles.keySet().size() == 1) {
            result.addAll(endInfiniteLoop());
        } else {
            result.addAll(0, bootstrap());
        }
        return result;
    }

    private static List<String> translateAll(Map<String, List<String>> vmFiles) {
        List<String> result = new ArrayList<>();
        for (String fileName : vmFiles.keySet()) {
            List<String> vmLines = vmFiles.get(fileName);
            result.addAll(VMParser.parse(fileName, vmLines));
        }
        return result;
    }

    private static List<String> bootstrap() {
        return List.of(
                initSP(),
                callSysInit()
        );
    }

    private static String callSysInit() {
        List<String> result =  VMParser.parse("", List.of("call Sys.init 0"));
        return String.join(System.lineSeparator(), result);
    }

    private static String initSP() {
        List<String> result = List.of(
                ASM.moveValueToD("256"),
                ASM.storeDToAddress("SP")
        );
        return String.join(System.lineSeparator(), result);
    }

    private static List<String> endInfiniteLoop() {
        return List.of(
                ASM.label("END"),
                ASM.moveValueToA("END"),
                ASM.jmp()
        );
    }
}
