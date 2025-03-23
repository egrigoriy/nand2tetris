import java.util.ArrayList;
import java.util.List;

public class VMProgram {
    public static final int SP = 0;
    public static final int LCL = 1;
    public static final int ARG = 2;
    public static final int THIS = 3;
    public static final int THAT = 4;
    public static final int temp = 5;

    private List<VMCommand> vmCommands = new ArrayList<>();

    public VMProgram(List<String> lines) {
        vmCommands = VMParser.parse(lines);
    }

    public List<String> toASM() {
        return null;
    }
}
