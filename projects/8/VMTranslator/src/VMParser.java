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
        return lines.stream()
                .map(VMParser::parse)
                .collect(Collectors.toList());
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
            case "label":
                return handleLabel(vmCommand);
            case "goto":
                return handleGoto(vmCommand);
            case "if-goto":
                return handleIfGoto(vmCommand);
            case "function":
                return handleFunction(vmCommand);
            case "return":
                return handleReturn();
            case "call":
                return handleCall(vmCommand);
        }
        return null;
    }

    private static String handleCall(String[] command) {
        String functionName = command[1];
        String nArgs = command[2];
        return ASMMacro.call(functionName, nArgs);
    }

    private static String handleReturn() {
        return ASMMacro.asmreturn();
    }

    private static String handleFunction(String[] command) {
        String functionName = command[1];
        String nVars = command[2];
        return ASMMacro.function(functionName, nVars);
    }

    private static String handleIfGoto(String[] command) {
        String labelName = command[1];
        return ASMMacro.ifGoto(labelName);
    }

    private static String handleGoto(String[] command) {
        String labelName = command[1];
        return ASMMacro.asmgoto(labelName);
    }

    private static String handleLabel(String[] command) {
        String labelName = command[1];
        return ASMMacro.label(labelName);
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
        return ASMMacro.popToMemory(segment, index);
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
