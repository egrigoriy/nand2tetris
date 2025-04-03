import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class VMParser {
    public static List<String> parse(List<String> lines) {
        return lines.stream()
                .map((line) -> {
                    String comment = "// " + line + System.lineSeparator();
                    return comment + parse(line);
                })
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

    private static String handlePush(String[] command) {
        String segment = command[1];
        String index = command[2];
        switch (segment) {
            case "constant":
                return ASMWriter.pushValue(index);
            case "local":
                return ASMWriter.pushLocal(index);
            case "argument":
                return ASMWriter.pushArgument(index);
            case "this":
                return ASMWriter.pushThis(index);
            case "that":
                return ASMWriter.pushThat(index);
            case "static":
                return ASMWriter.pushStatic(index);
            case "temp":
                return ASMWriter.pushTemp(index);
            case "pointer":
                return ASMWriter.pushPointer(index);
        }
        return null;
    }

    private static String handlePop(String[] command) {
        String segment = command[1];
        String index = command[2];
        switch (segment) {
            case "local":
                return ASMWriter.popLocal(index);
            case "argument":
                return ASMWriter.popArgument(index);
            case "this":
                return ASMWriter.popThis(index);
            case "that":
                return ASMWriter.popThat(index);
            case "static":
                return ASMWriter.popStatic(index);
            case "temp":
                return ASMWriter.popTemp(index);
            case "pointer":
                return ASMWriter.popPointer(index);
        }
        return null;
    }
    private static String handleAdd() {
        return ASMWriter.add();
    }

    private static String handleSub() {
        return ASMWriter.sub();
    }

    private static String handleLt() {
        return ASMWriter.lt();
    }

    private static String handleGt() {
        return ASMWriter.gt();
    }
    private static String handleEq() {
        return ASMWriter.eq();
    }


    private static String handleAnd() {
        return ASMWriter.and();
    }

    private static String handleOr() {
        return ASMWriter.or();
    }

    private static String handleNot() {
        return ASMWriter.not();
    }

    private static String handleNeg() {
        return ASMWriter.neg();
    }

    private static String handleCall(String[] command) {
        String functionName = command[1];
        String nArgs = command[2];
        return ASMWriter.call(functionName, nArgs);
    }

    private static String handleReturn() {
        return ASMWriter.ret();
    }

    private static String handleFunction(String[] command) {
        String functionName = command[1];
        String nVars = command[2];
        return ASMWriter.function(functionName, nVars);
    }

    private static String handleIfGoto(String[] command) {
        String labelName = command[1];
        return ASMWriter.ifGoto(labelName);
    }

    private static String handleGoto(String[] command) {
        String labelName = command[1];
        return ASMWriter.goTo(labelName);
    }

    private static String handleLabel(String[] command) {
        String labelName = command[1];
        return ASMWriter.label(labelName);
    }


}
