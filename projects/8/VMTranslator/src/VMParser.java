import java.util.List;
import java.util.stream.Collectors;

public class VMParser {
    private static String functionName = "defaultFunction";
    private static String currentfileName = "defaultFileName";

    public static List<String> parse(String fileName, List<String> vmLines) {
        currentfileName = fileName;
        return preprocess(vmLines).stream()
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
                String address = currentfileName + "." + index;
                return ASMWriter.pushStatic(address);
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
                String address = currentfileName + "." + index;
                return ASMWriter.popStatic(address);
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
        String calleeName = command[1];
        String nArgs = command[2];
        return ASMWriter.call(calleeName, nArgs);
    }

    private static String handleReturn() {
        return ASMWriter.ret();
    }

    private static String handleFunction(String[] command) {
        functionName = command[1];
        String nVars = command[2];
        return ASMWriter.function(functionName, nVars);
    }

    private static String handleIfGoto(String[] command) {
        String labelName = functionName + "$" + command[1];
        return ASMWriter.ifGoto(labelName);
    }

    private static String handleGoto(String[] command) {
        String labelName = functionName + "$" + command[1];
        return ASMWriter.goTo(labelName);
    }

    private static String handleLabel(String[] command) {
        String labelName = functionName + "$" + command[1];
        return ASMWriter.label(labelName);
    }


    private static List<String> preprocess(List<String> lines) {
        return removeComments(removeEmptyLines(lines));
    }

    private static List<String> removeEmptyLines(List<String> lines) {
        return lines.stream()
                .filter(el -> !el.trim().isEmpty())
                .collect(Collectors.toList());
    }

    private static List<String> removeComments(List<String> lines) {
        return lines.stream()
                .filter(el -> !el.trim().startsWith("//"))
                .map(el -> el.contains("/") ? el.substring(0, el.indexOf("/")) : el)
                .map(String::trim)
                .collect(Collectors.toList());
    }
}
