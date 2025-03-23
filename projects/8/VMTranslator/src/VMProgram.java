import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class VMProgram {
    public static final int SP = 0;
    public static final int LCL = 1;
    public static final int ARG = 2;
    public static final int THIS = 3;
    public static final int THAT = 4;
    public static final int temp = 5;

    private List<String> vmCommands = new ArrayList<>();

    public VMProgram(List<String> lines) {
        // remove empty lines
        vmCommands = lines.stream()
                .filter(el -> !el.trim().isEmpty())
                .collect(Collectors.toList());
        // remove comments
        vmCommands = vmCommands.stream()
                .filter(el -> !el.trim().startsWith("//"))
                .collect(Collectors.toList());
        vmCommands = VMParser.parse(vmCommands);
    }

    public List<String> toASM() {
        return vmCommands;
    }
}
