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
        }
        return null;
    }

    private static String handlePop(String[] command) {
       String segment = command[1];
       String index = command[2];
       return ASMMacro.popMemory(segment, index);
    }

    private static String handlePush(String[] command) {
        String segment = command[1];
        if (segment.equals("constant")) {
            int value = Integer.parseInt(command[2]);
            return ASMMacro.pushValue(value);
        }
        String index = command[2];
        return ASMMacro.pushMemory(segment, index);
    }
}
