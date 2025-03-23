import java.nio.charset.IllegalCharsetNameException;
import java.util.List;
import java.util.stream.Collectors;

public class VMParser {
    public static List<VMCommand> parse(List<String> lines) {
        return lines.stream().map(VMParser::parse).collect(Collectors.toList());
    }

    private static VMCommand parse(String line) {
        String[] vmCommand = line.split(" ");
        String operation = vmCommand[0];
        if (operation.equals("push")) {
            String segment = vmCommand[1];
            int address = Integer.parseInt(vmCommand[2]);
            return new PushCommand(segment, address);
        }
    }

    private static VMCommand handlePush(String[] command) {
        return

    }
}
