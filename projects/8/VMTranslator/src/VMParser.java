import java.util.List;
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
            case "push":
                return handlePush(vmCommand);
            case "pop":
                return handlePop(vmCommand);
            case "add":
                return handleAdd();
            case "sub":
                return handleSub();
            case "neg":
                return handleNeg();
            case "not":
                return handleNot();
            case "or":
                return handleOr();
            case "and":
                return handleAnd();
            case "eq":
                return handleEq();
            case "gt":
                return handleGt();
            case "lt":
                return handleLt();
        }
        return null;
    }

    private static String handleLt() {
        return ASMMacro.lt();
    }

    private static String handleGt() {
        return ASMMacro.gt();
    }

    private static String handleEq() {
        return ASMMacro.eq();
    }

    private static String handleAnd() {
        return ASMMacro.and();
    }

    private static String handleOr() {
        return ASMMacro.or();
    }

    private static String handleNot() {
        return ASMMacro.not();
    }

    private static String handleNeg() {
        return ASMMacro.neg();
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
        if (segment.equals("pointer") && index.equals("0")) {
            return ASMMacro.popThis();
        }
        if (segment.equals("pointer") && index.equals("1")) {
            return ASMMacro.popThat();
        }
        if (segment.equals("static")) {
            return ASMMacro.popStatic(index);
        }
        return ASMMacro.popMemory(segment, index);
    }

    private static String handlePush(String[] command) {
        String segment = command[1];
        String index = command[2];
        if (segment.equals("constant")) {
            return ASMMacro.pushValue(index);
        }
        if (segment.equals("temp")) {
            return ASMMacro.pushTemp(index);
        }
        if (segment.equals("pointer") && index.equals("0")) {
            return ASMMacro.pushThis();
        }
        if (segment.equals("pointer") && index.equals("1")) {
            return ASMMacro.pushThat();
        }
        if (segment.equals("static")) {
            return ASMMacro.pushStatic(index);
        }
        return ASMMacro.pushPointedMemory(segment, index);
    }
}
