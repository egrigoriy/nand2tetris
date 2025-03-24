import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class VMParser {
    public static final int SP = 0;
    public static final int LCL = 1;
    public static final int ARG = 2;
    public static final int THIS = 3;
    public static final int THAT = 4;
    public static final int temp = 5;
    public static List<String> parse(List<String> lines) {
        return lines.stream().map(VMParser::parse).collect(Collectors.toList());
    }

    private static String parse(String line) {
        String[] vmCommand = line.split(" ");
        String operation = vmCommand[0];
            switch (operation) {
                case "push": return handlePush(vmCommand);
                case "pop": return handlePop(vmCommand);
                case "add": return handleAdd();
                case "sub": return handleSub();
        }
        return null;
    }

    private static String handleSub() {
        return ASMMacro.sub();
    }

    private static String handleAdd() {
        return ASMMacro.add();
    }

    private static String handlePop(String[] command) {
       String segment = command[1];
       String index = command[2];
       if (segment.equals("temp")) {
           return ASMMacro.popTemp(index);
       }
       return ASMMacro.popMemory(segment, index);
    }

    private static String handlePush(String[] command) {
        String segment = command[1];
        if (segment.equals("constant")) {
            String value = command[2];
            return ASMMacro.pushValue(value);
        }
        if (segment.equals("temp")) {
            String index = command[2];
            return ASMMacro.pushTemp(index);
        }
        String index = command[2];
        return ASMMacro.pushMemory(segment, index);
    }
}
